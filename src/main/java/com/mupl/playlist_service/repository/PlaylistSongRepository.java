package com.mupl.playlist_service.repository;

import com.mupl.playlist_service.entity.PlaylistEntity;
import com.mupl.playlist_service.entity.PlaylistSongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSongEntity, Long> {
    boolean existsBySongId(Long id);
    List<PlaylistSongEntity> findAllByPlaylist(PlaylistEntity playlist);
    void deleteAllByPlaylist(PlaylistEntity playlist);
    void deleteAllBySongId(Long id);
}
