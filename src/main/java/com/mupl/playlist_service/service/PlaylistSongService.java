package com.mupl.playlist_service.service;

import com.mupl.playlist_service.dto.request.PlaylistSongRequest;
import com.mupl.playlist_service.dto.response.PlaylistSongResponse;

import java.util.List;

public interface PlaylistSongService {
    PlaylistSongResponse createPlaylistSong(long playlistId, PlaylistSongRequest playlistSongRequest);
    PlaylistSongResponse deletePlaylistSong(long playlistSongId);
    List<PlaylistSongResponse> getAllPlaylistSongsByPlaylistId(long playlistId);
    void deleteAllPlaylistSongsBySongId(long songId);
}
