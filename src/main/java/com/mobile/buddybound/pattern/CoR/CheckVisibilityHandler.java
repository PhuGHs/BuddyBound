package com.mobile.buddybound.pattern.CoR;

public class CheckVisibilityHandler implements AccessHandler {
    @Override
    public boolean checkAccess() {
        return false;
    }

    @Override
    public void setNext(AccessHandler accessHandler) {

    }
}
