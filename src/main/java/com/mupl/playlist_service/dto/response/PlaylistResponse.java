package com.mupl.playlist_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistResponse {
    private long playlistId;
    private String name;
    private Boolean isPublic;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyy HH:mm:ss")
    private LocalDateTime updatedAt;
}
