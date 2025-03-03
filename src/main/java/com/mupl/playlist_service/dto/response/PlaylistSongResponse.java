package com.mupl.playlist_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistSongResponse {
    private long id;
    private long songId;
    private String songName;
    private String artistNames;
    private long playlistId;
}
