package com.mobile.buddybound.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mobile.buddybound.model.dto.AlbumDto;
import com.mobile.buddybound.model.dto.Views;
import com.mobile.buddybound.service.AlbumService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/albums")
@SecurityRequirement(name = "bearerAuth")
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping
    @JsonView(Views.Read.class)
    public ResponseEntity<?> getAlbums(@RequestParam(required = false) Map<String, String> params) {
        return albumService.getAlbums(params);
    }

    @GetMapping("/{albumId}")
    @JsonView(Views.Read.class)
    public ResponseEntity<?> getAlbumById(@PathVariable long albumId) {
        return albumService.getAlbumById(albumId);
    }

    @DeleteMapping("/{albumId}")
    @JsonView(Views.Read.class)
    public ResponseEntity<?> deleteAlbum(@PathVariable Long albumId) {
        return albumService.deleteAlbumById(albumId);
    }

    @PostMapping("")
    @JsonView(Views.Read.class)
    public ResponseEntity<?> createAlbum(@RequestBody AlbumDto albumDto) {
        return albumService.createAlbum(albumDto);
    }
}
