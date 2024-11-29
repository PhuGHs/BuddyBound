package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.Group;
import com.mobile.buddybound.model.enumeration.GroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Override
    boolean existsById(Long aLong);

    @Query("SELECT g FROM Group g LEFT JOIN g.members m WHERE m.user.id = :userId AND g.groupType = :groupType")
    List<Group> findGroupsByUserAndGroupType(Long userId, GroupType groupType);
}
