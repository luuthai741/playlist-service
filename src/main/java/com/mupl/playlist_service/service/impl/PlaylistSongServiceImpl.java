package com.mupl.playlist_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mupl.playlist_service.dto.request.PlaylistSongRequest;
import com.mupl.playlist_service.dto.response.PlaylistSongResponse;
import com.mupl.playlist_service.dto.response.SongResponse;
import com.mupl.playlist_service.entity.PlaylistEntity;
import com.mupl.playlist_service.entity.PlaylistSongEntity;
import com.mupl.playlist_service.exception.BadRequestException;
import com.mupl.playlist_service.feign.MusicServiceClient;
import com.mupl.playlist_service.repository.PlaylistRepository;
import com.mupl.playlist_service.repository.PlaylistSongRepository;
import com.mupl.playlist_service.service.PlaylistSongService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class PlaylistSongServiceImpl implements PlaylistSongService {
    private final PlaylistSongRepository playlistSongRepository;
    private final PlaylistRepository playlistRepository;
    private final ModelMapper modelMapper;
    private final MusicServiceClient musicServiceClient;
    private final ObjectMapper objectMapper;

    @Override
    public PlaylistSongResponse createPlaylistSong(long playlistId, PlaylistSongRequest playlistSongRequest) {
        //todo: need write call api external to music service to get song information (can use OpenFeign package).
        // From that, whenever we get songs in playlist we can get data from database (or cache) to avoid external call
        SongResponse song = getSongBySongId(playlistSongRequest.getSongId());
        if (ObjectUtils.isEmpty(song)) {
            throw new BadRequestException("No song found with id " + playlistSongRequest.getSongId());
        }
        boolean songExists = playlistSongRepository.existsBySongId(playlistSongRequest.getSongId());
        if (songExists) {
            throw new BadRequestException("Song already exists in playlist");
        }
        String artistNames = song.getArtists().stream()
                .map(SongResponse.ArtistResponse::getName)
                .collect(Collectors.joining(" & "));
        PlaylistEntity playlistEntity = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new BadRequestException("Playlist id not found"));
        PlaylistSongEntity playlistSongEntity = PlaylistSongEntity.builder()
                .songId(playlistSongRequest.getSongId())
                .songName(song.getSongName())
                .artistNames(artistNames)
                .build();
        playlistSongEntity.setPlaylist(playlistEntity);
        return modelMapper.map(playlistSongRepository.save(playlistSongEntity), PlaylistSongResponse.class);
    }

    @Override
    public PlaylistSongResponse deletePlaylistSong(long playlistSongId) {
        PlaylistSongEntity playlistSongEntity = playlistSongRepository.findById(playlistSongId)
                .orElseThrow(() -> new BadRequestException("Song in playlist not found"));
        playlistSongRepository.delete(playlistSongEntity);
        return modelMapper.map(playlistSongEntity, PlaylistSongResponse.class);
    }

    @Override
    public List<PlaylistSongResponse> getAllPlaylistSongsByPlaylistId(long playlistId) {
        PlaylistEntity playlistEntity = playlistRepository.findById(playlistId).
                orElseThrow(() -> new BadRequestException("Playlist id not found"));
        return playlistSongRepository.findAllByPlaylist(playlistEntity)
                .stream()
                .map(s -> modelMapper.map(s, PlaylistSongResponse.class))
                .toList();
    }

    @Override
    public void deleteAllPlaylistSongsBySongId(long songId) {
        log.info("Deleting all playlist songs by song id: " + songId);
        playlistSongRepository.deleteAllBySongId(songId);
    }

    private SongResponse getSongBySongId(Long songId) {
        return objectMapper.convertValue(musicServiceClient.getSongById(songId), SongResponse.class);
    }
}
