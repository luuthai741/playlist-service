package com.mupl.playlist_service.controller;

import com.mupl.playlist_service.dto.request.PlaylistRequest;
import com.mupl.playlist_service.dto.response.PlaylistResponse;
import com.mupl.playlist_service.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

    @PostMapping("/playlists")
    public ResponseEntity<PlaylistResponse> createPlaylist(@RequestBody PlaylistRequest playlistRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playlistService.createPlaylist(playlistRequest));
    }

    @PutMapping("/playlists/{id}")
    public ResponseEntity<PlaylistResponse> updatePlaylist(@PathVariable long id, @RequestBody PlaylistRequest playlistRequest) {
        return ResponseEntity.ok().body(playlistService.updatePlaylist(id, playlistRequest));
    }

    @DeleteMapping("/playlists/{id}")
    public ResponseEntity<PlaylistResponse> deletePlaylist(@PathVariable long id) {
        return ResponseEntity.ok().body(playlistService.deletePlaylist(id));
    }

    @GetMapping("/playlists")
    public ResponseEntity<List<PlaylistResponse>> getPlaylists() {
        return ResponseEntity.ok().body(playlistService.getAllPlaylists());
    }
}
