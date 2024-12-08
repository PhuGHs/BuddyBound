package com.mobile.buddybound.pattern.CoR;

import com.mobile.buddybound.model.dto.AccessRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public abstract class BasePermissionHandler implements PermissionHandler {
    protected PermissionHandler next;
    @Override
    public void setNext(PermissionHandler h) {
        this.next = h;
    }

    @Override
    public boolean checkAccess(AccessRequest request) {
        if (Objects.isNull(next)) {
            log.info("End of chain");
            return true;
        }
        return next.checkAccess(request);
    }
}
