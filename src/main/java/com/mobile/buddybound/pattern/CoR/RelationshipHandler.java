package com.mobile.buddybound.pattern.CoR;

import com.mobile.buddybound.model.dto.AccessRequest;
import com.mobile.buddybound.repository.BlockedRelationshipRepository;
import com.mobile.buddybound.repository.MemberRepository;
import com.mobile.buddybound.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RelationshipHandler extends BasePermissionHandler {
    private final UserService userService;
    @Override
    public boolean checkAccess(AccessRequest request) {
        var currentUser = userService.getCurrentLoggedInUser();
        var userId = currentUser.getId();
        if (currentUser.getBlockedRelationships().stream().anyMatch(br -> br.getUser().getId().equals(userId))) {
            return false;
        }
        return super.checkAccess(request);
    }
}
