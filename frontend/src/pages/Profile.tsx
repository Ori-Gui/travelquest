
import { useState } from "react";
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import { mockCurrentUser, mockParties, mockDungeons } from "@/data/mockData";

const tabs = [
  { id: "profile", label: "프로필" },
  { id: "quests", label: "완료 기록" },
  { id: "reviews", label: "보낸 리뷰" },
  { id: "settings", label: "설정 변경" }
];

const Profile = () => {
  const [activeTab, setActiveTab] = useState("profile");
  
  const calculateLevelProgress = () => {
    const maxExp = mockCurrentUser.level * 50;
    return (mockCurrentUser.exp / maxExp) * 100;
  };

  const getClassColor = (userClass: string) => {
    switch (userClass) {
      case 'warrior':
        return 'text-red-600';
      case 'mage':
        return 'text-blue-600';
      case 'healer':
        return 'text-green-600';
      case 'ranger':
        return 'text-yellow-600';
      default:
        return '';
    }
  };

  const getClassDescription = (userClass: string) => {
    switch (userClass) {
      case 'warrior':
        return '전투력이 뛰어나며 체력이 높습니다. 주로 실외 활동과 스포츠에 적합합니다.';
      case 'mage':
        return '지식이 풍부하고 지혜롭습니다. 박물관, 유적지 탐방에 적합합니다.';
      case 'healer':
        return '팀을 돕고 분위기를 좋게 만듭니다. 조율과 협력이 필요한 여행에 적합합니다.';
      case 'ranger':
        return '길찾기와 탐험에 능숙합니다. 새로운 장소 발견과 모험에 적합합니다.';
      default:
        return '';
    }
  };

  const userCompletedQuests = [
    {
      id: "1",
      title: "경주당일여행",
      date: "05/03~05/04",
      completedTasks: 3,
      totalTasks: 4
    },
    {
      id: "2",
      title: "서울명소투어",
      date: "04/22~04/23",
      completedTasks: 2,
      totalTasks: 3
    }
  ];

  const userReviews = [
    {
      id: "1",
      dungeonName: "서울 명소 2곳 방문",
      rating: 4
    },
    {
      id: "2",
      dungeonName: "부산 야경 감상",
      rating: 5
    }
  ];

  return (
    <div className="min-h-screen flex flex-col">
      <Header />

      <main className="flex-grow p-4">
        <div className="flex flex-col items-center mb-4">
          <div className="w-20 h-20 bg-gray-300 rounded-full mb-2"></div>
          <h2 className="font-pixel text-lg">
            {mockCurrentUser.username} <span className="text-sm">Lv.{mockCurrentUser.level}</span>
          </h2>
          <div className="bg-quest-yellow rounded-full px-3 py-0.5 text-xs">
            👑 직업별 랭킹 3
          </div>
          <div className="w-full mt-2">
            <div className="pixel-progress-bar">
              <div 
                className="pixel-progress-bar-fill"
                style={{ width: `${calculateLevelProgress()}%` }}
              ></div>
            </div>
          </div>
        </div>

        <div className="mb-6">
          <button className="border border-black rounded-md px-3 py-1 text-sm">
            프로필 수정
          </button>
        </div>

        <div className="mb-6">
          <div className="flex border-b">
            {tabs.map(tab => (
              <button
                key={tab.id}
                onClick={() => setActiveTab(tab.id)}
                className={`tab-button ${activeTab === tab.id ? 'tab-active' : 'tab-inactive'}`}
              >
                {tab.label}
              </button>
            ))}
          </div>

          <div className="pt-4">
            {activeTab === "profile" && (
              <div>
                <div className="retro-border bg-white mb-4">
                  <h3 className="font-pixel text-md mb-2">🎭 직업 정보</h3>
                  <div className={`${getClassColor(mockCurrentUser.class)} font-pixel text-lg`}>
                    {mockCurrentUser.class.charAt(0).toUpperCase() + mockCurrentUser.class.slice(1)}
                  </div>
                  <p className="text-sm mt-2">{getClassDescription(mockCurrentUser.class)}</p>
                </div>

                <div className="retro-border bg-white mb-4">
                  <h3 className="font-pixel text-md mb-2">🧠 MBTI</h3>
                  <div className="font-bold">{mockCurrentUser.mbti}</div>
                  <p className="text-sm mt-1">🔮 함께하면 좋은 MBTI: INFP, ENFJ</p>
                </div>

                <div className="retro-border bg-white">
                  <h3 className="font-pixel text-md mb-2">🏆 달성 업적</h3>
                  <div className="flex flex-col space-y-2">
                    <div className="flex justify-between border-b pb-1">
                      <span>🌟 첫번째 여행</span>
                      <span className="text-quest-dark-green">완료</span>
                    </div>
                    <div className="flex justify-between border-b pb-1">
                      <span>📸 사진 5장 업로드</span>
                      <span className="text-quest-dark-green">완료</span>
                    </div>
                    <div className="flex justify-between pb-1">
                      <span>💬 리뷰 3개 작성</span>
                      <span className="text-gray-400">2/3</span>
                    </div>
                  </div>
                </div>
              </div>
            )}

            {activeTab === "quests" && (
              <div>
                {userCompletedQuests.map(quest => (
                  <div key={quest.id} className="retro-border bg-white mb-4">
                    <div className="flex justify-between">
                      <span className="font-pixel">✅ {quest.title}</span>
                      <span className="text-sm text-gray-500">{quest.date}</span>
                    </div>
                    <div className="mt-2 text-sm">
                      🏆 미션 {quest.completedTasks}/{quest.totalTasks} 완료
                    </div>
                  </div>
                ))}
              </div>
            )}
            
            {activeTab === "reviews" && (
              <div>
                {userReviews.map(review => (
                  <div key={review.id} className="retro-border bg-white mb-4">
                    <div className="flex justify-between items-start">
                      <span>💬 {review.dungeonName}</span>
                      <button className="text-xs border border-gray-300 rounded px-2 py-1">
                        삭제
                      </button>
                    </div>
                    <div className="flex mt-2">
                      {[...Array(5)].map((_, i) => (
                        <span key={i} className={i < review.rating ? "text-quest-yellow" : "text-gray-300"}>
                          ★
                        </span>
                      ))}
                    </div>
                  </div>
                ))}
              </div>
            )}
            
            {activeTab === "settings" && (
              <div className="space-y-4">
                <div className="retro-border bg-white">
                  <h3 className="font-pixel text-md mb-2">🔔 알림 설정</h3>
                  <div className="flex justify-between items-center border-b py-2">
                    <span>채팅 알림</span>
                    <label className="relative inline-flex items-center cursor-pointer">
                      <input type="checkbox" defaultChecked className="sr-only peer" />
                      <div className="w-11 h-6 bg-gray-200 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-quest-dark-green"></div>
                    </label>
                  </div>
                  <div className="flex justify-between items-center py-2">
                    <span>일정 알림</span>
                    <label className="relative inline-flex items-center cursor-pointer">
                      <input type="checkbox" defaultChecked className="sr-only peer" />
                      <div className="w-11 h-6 bg-gray-200 peer-focus:outline-none rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-quest-dark-green"></div>
                    </label>
                  </div>
                </div>
                
                <div className="flex justify-between">
                  <button className="text-sm text-gray-600 border border-gray-300 rounded-md px-4 py-2">
                    로그아웃
                  </button>
                  <button className="text-sm text-red-600 border border-red-300 rounded-md px-4 py-2">
                    계정 삭제
                  </button>
                </div>
              </div>
            )}
          </div>
        </div>
      </main>

      <Footer />
    </div>
  );
};

export default Profile;
