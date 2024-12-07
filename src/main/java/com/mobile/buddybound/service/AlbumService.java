package com.mobile.buddybound.service;

import com.mobile.buddybound.model.dto.AlbumDto;
import com.mobile.buddybound.model.entity.Album;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AlbumService {
    ResponseEntity<?> getAlbums(Map<String, String> params);
    ResponseEntity<?> getAlbumById(Long albumId);
    ResponseEntity<?> deleteAlbumById(Long albumId);
    ResponseEntity<?> createAlbum(AlbumDto dto);
}
