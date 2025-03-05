package com.mupl.playlist_service.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mupl.playlist_service.kafka.event.KafkaEvent;
import com.mupl.playlist_service.kafka.event.SongDeletePayload;
import com.mupl.playlist_service.service.PlaylistSongService;
import com.mupl.playlist_service.util.constant.KafkaEventType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SongConsumer {
    private final ObjectMapper objectMapper;
    private final PlaylistSongService playlistSongService;
    private final String SONG_TOPIC = "mupl_song_topic";
    private final String CONSUMER_GROUP = "mupl_playlist_consumer";

    @KafkaListener(topics = SONG_TOPIC, groupId = CONSUMER_GROUP, containerFactory = "kafkaListenerContainerFactory")
    public void consumeSongDeleteEvent(@Payload(required = false) byte[] message) {
        try {
            KafkaEvent<SongDeletePayload> event = objectMapper.readValue(message, KafkaEvent.class);
            KafkaEventType eventType = KafkaEventType.valueOf(event.getEventType());
            switch (eventType) {
                case SONG_DELETED -> deleteAllPlaylistSongsBySongId(event);
            }
        } catch (Exception e) {
            log.error("Error when handling delete song event", e);
        }
    }

    private void deleteAllPlaylistSongsBySongId(KafkaEvent event) {
        SongDeletePayload songDeletePayload = objectMapper.convertValue(event.getPayload(), SongDeletePayload.class);
        log.info("Received delete song event: {}", event);
        log.info("Start to process delete song event: {}", songDeletePayload);
        playlistSongService.deleteAllPlaylistSongsBySongId(songDeletePayload.getSongId());
        log.info("Finished to process delete song event: {}", event);
    }

    @KafkaListener(topics = "mupl_song_topic.DLQ", groupId = "dlq-group")
    public void handleDeadLetterQueue(byte[] message) {
        log.error("DLQ received message: {}", new String(message));
        // TODO: Lưu log hoặc gửi cảnh báo
    }
}
