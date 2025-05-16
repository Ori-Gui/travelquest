
import { ChatMessage, Dungeon, Party, Quest, QuestVerification, Review, User } from "@/types";

export const mockUsers: User[] = [
  {
    id: "1",
    username: "용사쿤",
    level: 3,
    exp: 75,
    class: "warrior",
    mbti: "ENFJ",
  },
  {
    id: "2",
    username: "마법사짱",
    level: 5,
    exp: 120,
    class: "mage",
    mbti: "INTJ",
  },
  {
    id: "3",
    username: "힐러왕",
    level: 4,
    exp: 95,
    class: "healer",
    mbti: "ISFJ",
  },
  {
    id: "4",
    username: "레인저마스터",
    level: 6,
    exp: 150,
    class: "ranger",
    mbti: "ESTP",
  },
];

export const mockDungeons: Dungeon[] = [
  {
    id: "1",
    title: "강원도하이킹 단체사진",
    location: "강원도",
    startDate: "2025-05-25",
    endDate: "2025-05-27",
    tags: ["하이킹", "자연", "사진", "식도락"],
    maxPartySize: 6,
    currentPartySize: 3,
    description: "강원도의 아름다운 자연과 함께하는 하이킹 여행, 멋진 단체사진도 찍어요!",
  },
  {
    id: "2",
    title: "제주 명소 탐방",
    location: "제주도",
    startDate: "2025-05-22",
    endDate: "2025-05-24",
    tags: ["명소", "자연", "사진"],
    maxPartySize: 5,
    currentPartySize: 2,
    description: "제주도의 숨은 명소들을 탐방하고 인생샷도 건져보세요!",
  },
  {
    id: "3",
    title: "서울 맛집 투어",
    location: "서울",
    startDate: "2025-06-01",
    endDate: "2025-06-02",
    tags: ["맛집", "도시", "식도락"],
    maxPartySize: 4,
    currentPartySize: 1,
    description: "서울의 다양한 맛집을 투어하며 미식 여행을 즐겨요!",
  },
];

export const mockParties: Party[] = [
  {
    id: "1",
    dungeonId: "1",
    leaderId: "1",
    members: [mockUsers[0], mockUsers[1], mockUsers[2]],
    status: "recruiting",
  },
  {
    id: "2",
    dungeonId: "2",
    leaderId: "2",
    members: [mockUsers[1], mockUsers[3]],
    status: "recruiting",
  },
  {
    id: "3",
    dungeonId: "3",
    leaderId: "3",
    members: [mockUsers[2]],
    status: "recruiting",
  },
];

export const mockQuests: Quest[] = [
  {
    id: "1",
    dungeonId: "1",
    title: "강원도에서 단체사진 찍기",
    description: "해운대 앞에서 전원 단체사진 찍기 (해가 질 때가 좋습니다)",
    completed: false,
    verificationNeeded: true,
    orderIndex: 1,
  },
  {
    id: "2",
    dungeonId: "1",
    title: "지역 맛집 투어 진행",
    description: "추천 맛집 3곳 이상 방문하고 사진 찍기",
    completed: false,
    verificationNeeded: true,
    orderIndex: 2,
  },
  {
    id: "3",
    dungeonId: "1",
    title: "지도에 없는 명소 찾기",
    description: "유명하지 않은 숨은 명소 발견하고 인증샷",
    completed: false,
    verificationNeeded: true,
    orderIndex: 3,
  },
];

export const mockQuestVerifications: QuestVerification[] = [
  {
    id: "1",
    questId: "1",
    userId: "1",
    verified: true,
    location: {
      latitude: 37.5665,
      longitude: 126.9780,
    },
  },
];

export const mockReviews: Review[] = [
  {
    id: "1",
    dungeonId: "1",
    userId: "1",
    title: "정말 즐거웠어요~",
    content: "처음 만난 사람들과 함께 여행했는데 너무 재밌었습니다. 다음에도 또 가고 싶어요!",
    rating: 5,
    createdAt: "2025-05-28",
  },
  {
    id: "2",
    dungeonId: "2",
    userId: "2",
    title: "좋은 경험이었습니다",
    content: "숨겨진 제주 명소를 많이 발견해서 좋았습니다. 파티원들도 친절했어요!",
    rating: 4,
    createdAt: "2025-05-25",
  },
];

export const mockChatMessages: ChatMessage[] = [
  {
    id: "1",
    roomId: "1",
    userId: "1",
    username: "용사쿤",
    message: "안녕하세요!",
    sentAt: "2025-05-20T10:30:00",
  },
  {
    id: "2",
    roomId: "1",
    userId: "2",
    username: "마법사짱",
    message: "안녕! 조율할게요~",
    sentAt: "2025-05-20T10:31:00",
  },
];

export const mockCurrentUser: User = {
  id: "1",
  username: "용사쿤",
  level: 3,
  exp: 75,
  class: "warrior",
  mbti: "ENFJ",
};

export const classMBTIMap: Record<UserClass, MBTIType[]> = {
  warrior: ["ESTJ", "ENTJ", "ESTP", "ENTP"],
  mage: ["INTJ", "INTP", "INFJ", "INFP"],
  healer: ["ISFJ", "ESFJ", "ISFP", "ESFP"],
  ranger: ["ISTP", "ISTJ", "ENFP", "ENFJ"]
};
