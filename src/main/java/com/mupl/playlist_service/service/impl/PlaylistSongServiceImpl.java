package com.mupl.playlist_service.service.impl;

import com.mupl.playlist_service.dto.request.PlaylistSongRequest;
import com.mupl.playlist_service.dto.response.PlaylistSongResponse;
import com.mupl.playlist_service.entity.PlaylistEntity;
import com.mupl.playlist_service.entity.PlaylistSongEntity;
import com.mupl.playlist_service.exception.BadRequestException;
import com.mupl.playlist_service.repository.PlaylistRepository;
import com.mupl.playlist_service.repository.PlaylistSongRepository;
import com.mupl.playlist_service.service.PlaylistSongService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class PlaylistSongServiceImpl implements PlaylistSongService {
    private final PlaylistSongRepository playlistSongRepository;
    private final PlaylistRepository playlistRepository;
    private final ModelMapper modelMapper;

    @Override
    public PlaylistSongResponse createPlaylistSong(long playlistId, PlaylistSongRequest playlistSongRequest) {
        //todo: need write call api external to music service to get song information (can use OpenFeign package).
        // From that, whenever we get songs in playlist we can get data from database (or cache) to avoid external call
        PlaylistEntity playlistEntity = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new BadRequestException("Playlist id not found"));

        boolean songExists = playlistSongRepository.existsByPlaylistAndSongId(playlistEntity, playlistSongRequest.getSongId());
        if (songExists) {
            throw new BadRequestException("Song already exists in playlist.");
        }

        // Tạo entity mới
        PlaylistSongEntity playlistSongEntity = modelMapper.map(playlistSongRequest, PlaylistSongEntity.class);
        playlistSongEntity.setPlaylist(playlistEntity);

        // Lưu entity
        PlaylistSongEntity savedEntity = playlistSongRepository.save(playlistSongEntity);

        // Trả về response
        PlaylistSongResponse response = modelMapper.map(savedEntity, PlaylistSongResponse.class);
        response.setPlaylistId(playlistEntity.getPlaylistId());

        return response;
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
}
