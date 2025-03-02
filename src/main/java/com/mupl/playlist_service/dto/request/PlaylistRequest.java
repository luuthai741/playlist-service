package com.mupl.playlist_service.dto.request;

import com.mupl.playlist_service.util.constant.PlaylistType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistRequest {
    private PlaylistType type;
    private String name;
    private Boolean isPublic;
}
