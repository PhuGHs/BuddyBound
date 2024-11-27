package com.mobile.buddybound.repository;

import com.mobile.buddybound.model.entity.Member;
import com.mobile.buddybound.model.enumeration.GroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUser_IdAndGroup_GroupType(Long userId, GroupType groupType);

    boolean existsByUser_IdAndGroup_Id(Long userId, Long groupId);

    boolean existsByUser_IdAndGroup_IdAndIsAdmin(Long userId, Long groupId, boolean isAdmin);

    Optional<Member> getMemberByUser_IdAndGroup_Id(Long userId, Long groupId);

    @Query("SELECT m FROM Member m where m.group.id = :groupId and m.isApproved = :isApproved")
    List<Member> getAllMembers(Long groupId, boolean isApproved);
}
