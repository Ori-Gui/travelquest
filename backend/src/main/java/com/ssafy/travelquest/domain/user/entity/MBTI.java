package com.ssafy.travelquest.domain.user.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static Map<String, List<String>> getJobToMbtiMap() {
        Map<String, List<String>> result = new HashMap<>();

        for (MBTI mbti : MBTI.values()) {
            String job = mbti.getJobClassCode();
            if (job == null) continue; // NONE 필터링

            result.computeIfAbsent(job, k -> new ArrayList<>())
                    .add(mbti.name());
        }

        return result;
    }
}

