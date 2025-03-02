package com.mupl.playlist_service.service.impl;

import com.mupl.playlist_service.dto.request.PlaylistRequest;
import com.mupl.playlist_service.dto.response.PlaylistResponse;
import com.mupl.playlist_service.entity.PlaylistEntity;
import com.mupl.playlist_service.exception.BadRequestException;
import com.mupl.playlist_service.repository.PlaylistRepository;
import com.mupl.playlist_service.repository.PlaylistSongRepository;
import com.mupl.playlist_service.service.PlaylistService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final ModelMapper modelMapper;

    @Override
    public PlaylistResponse createPlaylist(PlaylistRequest playlistRequest) {
        //todo: integration spring security to get username. For now, set username is anonymous in temporary
        PlaylistEntity playlistEntity = new PlaylistEntity();
        modelMapper.map(playlistRequest, playlistEntity);
        playlistEntity.setCreatedAt(LocalDateTime.now());
        playlistEntity.setUpdatedAt(LocalDateTime.now());
        playlistEntity.setUsername("anonymous");
        return modelMapper.map(playlistRepository.save(playlistEntity), PlaylistResponse.class);
    }

    @Override
    public PlaylistResponse updatePlaylist(Long playlistId, PlaylistRequest playlistRequest) {
        PlaylistEntity playlistEntity = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new BadRequestException("Playlist is not found"));
        playlistEntity.setType(playlistRequest.getType());
        playlistEntity.setName(playlistRequest.getName());
        playlistEntity.setIsPublic(playlistRequest.getIsPublic());
        playlistEntity.setUpdatedAt(LocalDateTime.now());
        return modelMapper.map(playlistRepository.save(playlistEntity), PlaylistResponse.class);
    }

    @Override
    public List<PlaylistResponse> getAllPlaylists() {
        return playlistRepository.findAll()
                .stream()
                .map(playlistEntity -> modelMapper.map(playlistEntity, PlaylistResponse.class))
                .toList();
    }

    @Override
    public PlaylistResponse deletePlaylist(Long playlistId) {
        PlaylistEntity playlistEntity = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new BadRequestException("Playlist is not found"));
        playlistSongRepository.deleteAllByPlaylist(playlistEntity);
        PlaylistResponse playlistResponse = modelMapper.map(playlistEntity, PlaylistResponse.class);
        playlistRepository.delete(playlistEntity);
        return playlistResponse;
    }
}
