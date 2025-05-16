
import { useState } from "react";
import { Link } from "react-router-dom";
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import { mockParties, mockDungeons } from "@/data/mockData";

const tabs = [
  { id: "current", label: "현재 모집" },
  { id: "joined", label: "참여 기록" },
  { id: "previous", label: "보낸 리뷰" },
  { id: "settings", label: "오픈 예정" }
];

const PartyList = () => {
  const [activeTab, setActiveTab] = useState("current");

  const getDungeonById = (id: string) => {
    return mockDungeons.find(dungeon => dungeon.id === id);
  };

  const formatDate = (dateStr: string) => {
    const options: Intl.DateTimeFormatOptions = { month: 'numeric', day: 'numeric' };
    return new Date(dateStr).toLocaleDateString('ko-KR', options);
  };

  return (
    <div className="min-h-screen flex flex-col">
      <Header />

      <main className="flex-grow p-4">
        <h1 className="font-pixel text-xl mb-4">🔥 모험 진행 현황</h1>

        {/* Current quests */}
        <div className="mb-6">
          {mockParties.map(party => {
            const dungeon = getDungeonById(party.dungeonId);
            if (!dungeon) return null;
            
            return (
              <div key={party.id} className="retro-border bg-white mb-4">
                <h3 className="font-pixel text-lg mb-2">
                  제주 동부 중문 명소
                </h3>
                <div className="flex justify-between text-sm mb-2">
                  <div>기간: {formatDate(dungeon.startDate)}-{formatDate(dungeon.endDate)}</div>
                  <div>완료: 2/4</div>
                </div>
                <div className="pixel-progress-bar">
                  <div className="pixel-progress-bar-fill" style={{ width: '50%' }}></div>
                </div>
                <Link to={`/dungeons/${dungeon.id}`}>
                  <button className="pixel-button-alt w-full">
                    자세히 보기
                  </button>
                </Link>
              </div>
            );
          })}
        </div>

        {/* My Check-ins */}
        <h2 className="font-pixel text-lg mb-2">✏️ 내 인증 정보</h2>
        <div className="space-y-3 mb-8">
          <div className="retro-border bg-white">
            <div className="flex justify-between items-center">
              <div>
                <span className="text-sm">【제주】 서귀 남쪽 2곳 방문</span>
                <div className="text-xs text-quest-dark-green">완료!</div>
              </div>
              <Link to="/quests/1">
                <button className="bg-quest-dark-green text-white px-3 py-1 rounded text-sm">
                  보기
                </button>
              </Link>
            </div>
          </div>
          
          <div className="retro-border bg-white">
            <div className="flex justify-between items-center">
              <div>
                <span className="text-sm">【부산】 해운대에서 일출</span>
                <div className="text-xs text-quest-dark-green">완료!</div>
              </div>
              <Link to="/quests/2">
                <button className="bg-quest-dark-green text-white px-3 py-1 rounded text-sm">
                  보기
                </button>
              </Link>
            </div>
          </div>
        </div>

        {/* Current Party Settings */}
        <h2 className="font-pixel text-lg mb-2">⚙️ 현재 파티 설정</h2>
        <div className="space-y-3 mb-8">
          <div className="retro-border bg-white">
            <div className="flex justify-between items-center">
              <span className="text-sm">👑 운영자 양도하기</span>
              <button className="border border-black px-3 py-1 rounded text-sm">
                변경
              </button>
            </div>
          </div>
          
          <div className="retro-border bg-white">
            <div className="flex justify-between items-center">
              <span className="text-sm">👋 참가자 관리</span>
              <button className="border border-black px-3 py-1 rounded text-sm">
                변경
              </button>
            </div>
          </div>
          
          <div className="retro-border bg-white">
            <div className="flex justify-between items-center">
              <span className="text-sm">🔒 파티 비공개하기</span>
              <button className="border border-black px-3 py-1 rounded text-sm">
                변경
              </button>
            </div>
          </div>
        </div>

        <button className="pixel-button-danger w-full">
          ⚠️ 던전 취소하기
        </button>
      </main>

      <Footer />
    </div>
  );
};

export default PartyList;
