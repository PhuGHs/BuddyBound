package com.mobile.buddybound.pattern.CoR;

public interface AccessHandler {
    boolean checkAccess();
    void setNext(AccessHandler accessHandler);
}
