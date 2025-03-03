package com.mupl.playlist_service.controller.internal;

import com.mupl.playlist_service.service.PlaylistSongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/playlists-service")
@RequiredArgsConstructor
public class PlaylistSongInternalController {
    private final PlaylistSongService playlistSongService;

    @DeleteMapping("/songs/{songId}")
    public ResponseEntity<String> deletePlaylistSongsBySongId(@PathVariable Long songId) {
        playlistSongService.deleteAllPlaylistSongsBySongId(songId);
        return ResponseEntity.ok().build();
    }
}
