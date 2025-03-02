package com.mupl.playlist_service.service;

import com.mupl.playlist_service.dto.request.PlaylistRequest;
import com.mupl.playlist_service.dto.response.PlaylistResponse;

import java.util.List;

public interface PlaylistService {
    PlaylistResponse createPlaylist(PlaylistRequest playlistRequest);
    PlaylistResponse updatePlaylist(Long playlistId, PlaylistRequest playlistRequest);
    List<PlaylistResponse> getAllPlaylists();
    PlaylistResponse deletePlaylist(Long playlistId);
}
