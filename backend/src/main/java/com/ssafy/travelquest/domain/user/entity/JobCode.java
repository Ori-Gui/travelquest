package com.ssafy.travelquest.domain.user.entity;

import lombok.Getter;

@Getter
public enum JobCode {
    NONE("없음", "여행을 함께하는 사람들 중에서 나의 역할을 정하지 않은 상태."),
    WARRIOR("전사", "파티의 선두에서 일정을 이끌고 추진하는 유형. 여행 일정을 밀고 나가는 핵심 멤버."),
    MAGE("마법사", "여행지를 분석하고, 최적의 루트를 계산하는 브레인. 계획 수립에 강함."),
    ROGUE("도적", "계획보다는 분위기를 따라가는 감성 여행자. 숨은 명소를 잘 찾아냄."),
    HEALER("힐러", "팀 내 분위기를 조율하고, 감성적 순간을 만드는 공감 캐릭터."),
    BARD("바드", "모두를 연결하고 시너지를 내는 매칭형 캐릭터. 흥 많은 리액션 담당."),
    RANGER("레인저", "자연이나 숨은 여행지를 즐기며, 때로는 솔로잉도 선호하는 탐험가형."),
    MECHANIC("메카닉", "여행 중 문제 상황 대처에 강한 실전파. 렌터카, 네비, 예산 등 처리에 강함."),
    TRICKSTER("트릭스터", "여행지에서 새로운 인연 만들기에 특화된 사교성 최강자. 분위기 주도도 잘함.");

    private final String displayName;
    private final String description;

    JobCode(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}