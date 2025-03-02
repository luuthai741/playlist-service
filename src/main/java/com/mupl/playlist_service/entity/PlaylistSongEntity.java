package com.mupl.playlist_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mupl_song")
public class PlaylistSongEntity {
    @Id
    private Long songId;
    private String songName;
    private String artistNames;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private PlaylistEntity playlist;
}
