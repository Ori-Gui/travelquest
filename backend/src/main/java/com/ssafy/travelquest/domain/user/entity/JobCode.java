package com.ssafy.travelquest.domain.user.entity;

import lombok.Getter;

@Getter
public enum JobCode {
    NONE("없음"),
    WARRIOR("전사"),
    MAGE("마법사"),
    ROGUE("도적"),
    HEALER("힐러"),
    BARD("바드"),
    RANGER("레인저"),
    MECHANIC("메카닉"),
    TRICKSTER("트릭스터");

    private final String displayName;

    JobCode(String displayName) {
        this.displayName = displayName;
    }

}