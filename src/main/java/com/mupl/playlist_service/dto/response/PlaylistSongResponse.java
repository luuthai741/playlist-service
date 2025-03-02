package com.mupl.playlist_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistSongResponse {
    private long songId;
    private String songName;
    private int artistId;
    private String artistName;
    private long playlistId;
}
