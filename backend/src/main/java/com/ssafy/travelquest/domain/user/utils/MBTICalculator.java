package com.ssafy.travelquest.domain.user.utils;

import com.ssafy.travelquest.domain.user.dto.MbtiAnswer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MBTICalculator {

    public static String calculate(List<MbtiAnswer> answers) {
        Map<String, Integer> scoreMap = new HashMap<>();

        for (MbtiAnswer answer : answers) {
            scoreMap.merge(answer.getType(), answer.getValue(), Integer::sum);
        }

        StringBuilder mbti = new StringBuilder();
        mbti.append(scoreMap.getOrDefault("EI", 0) >= 2 ? "E" : "I");
        mbti.append(scoreMap.getOrDefault("SN", 0) >= 2 ? "S" : "N");
        mbti.append(scoreMap.getOrDefault("TF", 0) >= 2 ? "T" : "F");
        mbti.append(scoreMap.getOrDefault("JP", 0) >= 2 ? "J" : "P");

        return mbti.toString();
    }
}
