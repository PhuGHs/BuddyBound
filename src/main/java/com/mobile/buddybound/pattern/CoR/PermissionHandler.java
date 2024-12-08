package com.mobile.buddybound.pattern.CoR;

import com.mobile.buddybound.model.dto.AccessRequest;

public interface PermissionHandler {
    void setNext(PermissionHandler h);
    boolean checkAccess(AccessRequest request);
}
