package com.mobile.buddybound.pattern.CoR;

public class CheckGroupHandler implements AccessHandler {
    @Override
    public boolean checkAccess() {
        return false;
    }

    @Override
    public void setNext(AccessHandler accessHandler) {

    }
}
