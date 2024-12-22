package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.exception.NotFoundException;
import com.mobile.buddybound.model.dto.AlbumDto;
import com.mobile.buddybound.model.entity.Album;
import com.mobile.buddybound.model.response.ApiResponse;
import com.mobile.buddybound.model.response.ApiResponseStatus;
import com.mobile.buddybound.repository.AlbumRepository;
import com.mobile.buddybound.service.AlbumService;
import com.mobile.buddybound.service.UserService;
import com.mobile.buddybound.service.mapper.AlbumMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AlbumServiceImpl implements AlbumService {
    private static final Logger log = LoggerFactory.getLogger(AlbumServiceImpl.class);
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAlbums(Map<String, String> params) {
        Long currentUserId = userService.getCurrentLoggedInUser().getId();
        String searchTerm = params.get("searchTerm");
        String start = params.get("start");
        String end = params.get("end");

        Specification<Album> spec = Specification.where(isOwnedBy(currentUserId)
                .and(hasSearchTerm(searchTerm))
                .and(isBetweenStartAndEnd(start, end))
        );
        List<AlbumDto> dtoList = albumRepository.findAll(spec, Sort.by("createdAt").descending()).stream().map(albumMapper::toDto).toList();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get albums", dtoList));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getAlbumById(Long albumId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new NotFoundException("Album not found"));
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Get album by id", albumMapper.toDto(album)));
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteAlbumById(Long albumId) {
        var currentUserId = userService.getCurrentLoggedInUser().getId();
        if (!albumRepository.existsByIdAndUser_Id(albumId, currentUserId)) {
            throw new NotFoundException("Album not found");
        }
        albumRepository.deleteById(albumId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional
    public ResponseEntity<?> createAlbum(AlbumDto dto) {
        var currentUser = userService.getCurrentLoggedInUser();
        Album entity = albumMapper.toEntity(dto);
        entity.setId(dto.getId());
        entity.setUser(currentUser);
        entity = albumRepository.save(entity);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Create album", albumMapper.toDto(entity)));
    }

    private Specification<Album> isOwnedBy(Long userId) {
        return (root, query, cb) -> cb.equal(root.get("user").get("id"), userId);
    }
    private Specification<Album> hasSearchTerm(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(root.get("title"), "%" + searchTerm + "%");
        };
    }
    private Specification<Album> isBetweenStartAndEnd(String start, String end) {
        return (root, query, cb) -> {
            if (Objects.isNull(start) && Objects.isNull(end)) {
                return cb.conjunction();
            }
            var startDate = start != null ? LocalDate.parse(start) : null;
            var endDate = end != null ? LocalDate.parse(end) : null;

            if (!Objects.isNull(startDate) && !Objects.isNull(endDate)) {
                return cb.between(root.get("createdAt"), startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
            } else if (startDate != null) {
                return cb.greaterThanOrEqualTo(root.get("createdAt"), startDate.atStartOfDay());
            } else {
                return cb.lessThanOrEqualTo(root.get("createdAt"), endDate.atTime(LocalTime.MAX));
            }
        };
    }
}
