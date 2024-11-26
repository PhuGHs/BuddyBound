package com.mobile.buddybound.repository.specification;

import com.mobile.buddybound.model.entity.Relationship;
import com.mobile.buddybound.model.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class RelationshipSpecification {
    public static <T> Specification<T> hasCurrentUserId(Long currentUserId, Class<T> entityClass) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.equal(root.get("sender").get("id"), currentUserId),
                criteriaBuilder.equal(root.get("receiver").get("id"), currentUserId)
        );
    }

    public static <T> Specification<T> hasPendingStatus(boolean isPending, Class<T> entityClass) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isPending"), isPending);
    }

    public static <T> Specification<T> hasSearchTerm(String searchTerm, Class<T> entityClass) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm == null || searchTerm.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Predicate searchPredicate = criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("sender").get("fullName")), "%" + searchTerm.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("receiver").get("fullName")), "%" + searchTerm.toLowerCase() + "%")
            );

            return criteriaBuilder.and(searchPredicate);
        };
    }

    public static <T> Specification<T> hasUserAndFullNameMatch(Long userId, String fullName, Class<T> entityClass) {
        return (root, query, cb) -> {
            assert query != null;
            query.distinct(true);

            if (fullName == null || fullName.isEmpty()) {
                return cb.conjunction();
            }

            Join<Relationship, User> senderJoin = root.join("sender", JoinType.LEFT);
            Join<Relationship, User> receiverJoin = root.join("receiver", JoinType.LEFT);

            Predicate senderMatch = cb.and(
                    cb.equal(root.get("receiver").get("id"), userId),
                    cb.like(cb.lower(senderJoin.get("fullName")),
                            "%" + fullName.toLowerCase() + "%")
            );

            Predicate receiverMatch = cb.and(
                    cb.equal(root.get("sender").get("id"), userId),
                    cb.like(cb.lower(receiverJoin.get("fullName")),
                            "%" + fullName.toLowerCase() + "%")
            );

            return cb.or(senderMatch, receiverMatch);
        };
    }
}
