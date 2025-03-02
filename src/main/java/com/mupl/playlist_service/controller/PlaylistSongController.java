package com.mupl.playlist_service.controller;

import com.mupl.playlist_service.dto.request.PlaylistSongRequest;
import com.mupl.playlist_service.dto.response.PlaylistSongResponse;
import com.mupl.playlist_service.service.PlaylistSongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class PlaylistSongController {
    private final PlaylistSongService playlistSongService;

    @PostMapping("/playlists/{playlistId}/songs")
    public ResponseEntity<PlaylistSongResponse> createPlaylistSong(@PathVariable long playlistId, @RequestBody PlaylistSongRequest playlistSongRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playlistSongService.createPlaylistSong(playlistId, playlistSongRequest));
    }

    @DeleteMapping("/playlists/{playlistId}/songs/{songId}")
    public ResponseEntity<PlaylistSongResponse> deletePlaylistSong(@PathVariable long playlistId, @PathVariable long songId) {
        log.info("Delete playlist {} song id {}", playlistId, songId);
        return ResponseEntity.ok().body(playlistSongService.deletePlaylistSong(songId));
    }

    @GetMapping("/playlists/{playlistId}/songs")
    public ResponseEntity<List<PlaylistSongResponse>> getSongsByPlaylist(@PathVariable long playlistId) {
        return ResponseEntity.ok().body(playlistSongService.getAllPlaylistSongsByPlaylistId(playlistId));
    }
}
