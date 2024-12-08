package com.mobile.buddybound.pattern.CoR;

import com.mobile.buddybound.model.dto.AccessRequest;
import com.mobile.buddybound.repository.PostVisibilityRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostVisibilityHandler extends BasePermissionHandler {
    private final PostVisibilityRepository postVisibilityRepository;
    @Override
    public boolean checkAccess(AccessRequest request) {
        if (postVisibilityRepository.existsByPost_IdAndMember_User_Id(request.getPostId(), request.getViewerId())) {
            return super.checkAccess(request);
        }
        return false;
    }
}
