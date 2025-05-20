package com.ssafy.travelquest.domain.user.entity;

import lombok.Getter;

@Getter
public enum MBTI {
    NONE(null),
    ISTJ("MECHANIC"),
    ISFJ("RANGER"),
    INFJ("HEALER"),
    INTJ("WIZARD"),
    ISTP("RANGER"),
    ISFP("THIEF"),
    INFP("HEALER"),
    INTP("WIZARD"),
    ESTP("MECHANIC"),
    ESFP("THIEF"),
    ENFP("BARD"),
    ENTP("BARD"),
    ESTJ("WARRIOR"),
    ESFJ("TRICKSTER"),
    ENFJ("TRICKSTER"),
    ENTJ("WARRIOR");

    private final String jobClassCode;

    MBTI(String jobClassCode) {
        this.jobClassCode = jobClassCode;
    }

    public String getJobClassCode() {
        return jobClassCode;
    }
}

