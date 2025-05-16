
export type UserClass = 'warrior' | 'mage' | 'healer' | 'ranger';

export type MBTIType = 'ISTJ' | 'ISFJ' | 'INFJ' | 'INTJ' | 'ISTP' | 'ISFP' | 'INFP' | 'INTP' | 
                      'ESTP' | 'ESFP' | 'ENFP' | 'ENTP' | 'ESTJ' | 'ESFJ' | 'ENFJ' | 'ENTJ';

export interface User {
  id: string;
  username: string;
  level: number;
  exp: number;
  class: UserClass;
  mbti: MBTIType;
  avatar?: string;
}

export interface Dungeon {
  id: string;
  title: string;
  location: string;
  startDate: string;
  endDate: string;
  tags: string[];
  maxPartySize: number;
  currentPartySize: number;
  description?: string;
  imageUrl?: string;
}

export interface Party {
  id: string;
  dungeonId: string;
  leaderId: string;
  members: User[];
  status: 'recruiting' | 'full' | 'in-progress' | 'completed';
}

export interface Quest {
  id: string;
  dungeonId: string;
  title: string;
  description: string;
  completed: boolean;
  verificationNeeded: boolean;
  orderIndex: number;
}

export interface QuestVerification {
  id: string;
  questId: string;
  userId: string;
  photoUrl?: string;
  verified: boolean;
  location?: {
    latitude: number;
    longitude: number;
  };
}

export interface Review {
  id: string;
  dungeonId: string;
  userId: string;
  title: string;
  content: string;
  rating: number;
  createdAt: string;
  images?: string[];
}

export interface ChatMessage {
  id: string;
  roomId: string;
  userId: string;
  username: string;
  message: string;
  sentAt: string;
}
