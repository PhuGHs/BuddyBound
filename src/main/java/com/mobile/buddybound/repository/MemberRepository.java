package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.Member;
import com.mobile.buddybound.model.enumeration.GroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUser_IdAndGroup_GroupType(Long userId, GroupType groupType);
}
