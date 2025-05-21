package com.ssafy.travelquest.domain.party.entity;

public enum PartyStatus {
    MATCHING("매칭중"),
    IN_PROGRESS("진행중"),
    COMPLETED("완료"),
    FAILED("실패");
    private final String displayName;
    PartyStatus(String displayName) {
        this.displayName = displayName;
    }
}
