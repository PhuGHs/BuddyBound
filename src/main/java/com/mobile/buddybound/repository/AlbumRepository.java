package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>, JpaSpecificationExecutor<Album> {
    boolean existsByIdAndUser_Id(Long id, Long user_id);
}
