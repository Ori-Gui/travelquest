
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import QuestCard from "@/components/QuestCard";
import PartyMember from "@/components/PartyMember";
import { mockDungeons, mockQuests, mockParties, mockUsers } from "@/data/mockData";

enum VerificationStatus {
  PENDING,
  CAPTURING,
  SUBMITTED
}

const DungeonDetail = () => {
  const { id } = useParams<{ id: string }>();
  const [activeTab, setActiveTab] = useState("info");
  const [verificationStatus, setVerificationStatus] = useState<VerificationStatus>(VerificationStatus.PENDING);
  const [selectedQuest, setSelectedQuest] = useState<string | null>(null);
  
  // Find the dungeon and related data
  const dungeon = mockDungeons.find(d => d.id === id);
  const quests = mockQuests.filter(q => q.dungeonId === id);
  const party = mockParties.find(p => p.dungeonId === id);
  
  if (!dungeon) {
    return (
      <div className="min-h-screen flex flex-col">
        <Header />
        <main className="flex-grow p-4 flex items-center justify-center">
          <div className="text-center">
            <h2 className="font-pixel text-xl mb-2">던전을 찾을 수 없습니다</h2>
            <p>요청하신 던전 정보가 존재하지 않습니다.</p>
          </div>
        </main>
        <Footer />
      </div>
    );
  }
  
  const handleVerifyQuest = (questId: string) => {
    setSelectedQuest(questId);
    setVerificationStatus(VerificationStatus.CAPTURING);
  };
  
  const handleCaptureComplete = () => {
    // Simulate photo upload
    setVerificationStatus(VerificationStatus.SUBMITTED);
    
    // Simulate verification process
    setTimeout(() => {
      setVerificationStatus(VerificationStatus.PENDING);
      setSelectedQuest(null);
    }, 2000);
  };
  
  const formatDate = (dateString: string) => {
    const options: Intl.DateTimeFormatOptions = { month: 'numeric', day: 'numeric' };
    return new Date(dateString).toLocaleDateString('ko-KR', options);
  };

  return (
    <div className="min-h-screen flex flex-col">
      <Header />
      
      <main className="flex-grow p-4">
        {/* Dungeon header */}
        <div className="retro-border bg-white mb-4">
          <p className="text-sm">
            🗓️ 기간: {formatDate(dungeon.startDate)} ~ {formatDate(dungeon.endDate)}
          </p>
          <p className="text-sm">
            📍 던전명: No.{dungeon.id}, {dungeon.title}
          </p>
          <div className="flex flex-wrap gap-1 mt-2">
            {dungeon.tags.map((tag, index) => (
              <span key={index} className="text-xs bg-quest-yellow px-2 py-0.5 rounded-full">
                {tag}
              </span>
            ))}
          </div>
        </div>
        
        {/* Tab navigation */}
        <div className="flex space-x-1 mb-4">
          <button 
            onClick={() => setActiveTab("info")}
            className={`tab-button ${activeTab === "info" ? 'tab-active' : 'tab-inactive'}`}
          >
            정보
          </button>
          <button 
            onClick={() => setActiveTab("quests")}
            className={`tab-button ${activeTab === "quests" ? 'tab-active' : 'tab-inactive'}`}
          >
            퀘스트
          </button>
          <button 
            onClick={() => setActiveTab("party")}
            className={`tab-button ${activeTab === "party" ? 'tab-active' : 'tab-inactive'}`}
          >
            파티
          </button>
          <button 
            onClick={() => setActiveTab("chat")}
            className={`tab-button ${activeTab === "chat" ? 'tab-active' : 'tab-inactive'}`}
          >
            채팅
          </button>
        </div>
        
        {/* Tab content */}
        <div className="mb-6">
          {/* Info tab */}
          {activeTab === "info" && (
            <div>
              <div className="retro-border bg-white mb-4">
                <h3 className="font-pixel text-lg mb-2">🗺️ 던전 소개</h3>
                <p className="text-sm">{dungeon.description || "던전 소개가 없습니다."}</p>
              </div>
              
              <div className="retro-border bg-white mb-4">
                <h3 className="font-pixel text-lg mb-2">⏰ 일정 정보</h3>
                <p className="text-sm">시작: {dungeon.startDate}</p>
                <p className="text-sm">종료: {dungeon.endDate}</p>
                <p className="text-sm">최대 인원: {dungeon.maxPartySize}명</p>
              </div>
              
              <div className="flex justify-between mt-4">
                <button className="pixel-button w-full mr-1">
                  참여 신청
                </button>
                <button className="pixel-button-danger w-full ml-1">
                  찜하기
                </button>
              </div>
            </div>
          )}
          
          {/* Quests tab */}
          {activeTab === "quests" && (
            <div>
              <h3 className="font-pixel text-lg mb-2">📋 던전 퀘스트</h3>
              {quests.map((quest, idx) => (
                <QuestCard 
                  key={quest.id} 
                  quest={quest} 
                  index={idx} 
                  onVerify={handleVerifyQuest}
                />
              ))}
              
              {verificationStatus === VerificationStatus.CAPTURING && (
                <div className="fixed inset-0 bg-black bg-opacity-70 flex items-center justify-center p-4 z-50">
                  <div className="bg-white rounded-lg p-4 w-full max-w-md">
                    <h3 className="font-pixel text-lg mb-4 text-center">📸 사진 인증</h3>
                    <div className="bg-gray-200 h-60 rounded-lg flex items-center justify-center mb-4">
                      <p className="text-gray-500">카메라 영역</p>
                    </div>
                    <div className="flex space-x-2">
                      <button 
                        className="pixel-button-danger w-1/2"
                        onClick={() => setVerificationStatus(VerificationStatus.PENDING)}
                      >
                        취소
                      </button>
                      <button 
                        className="pixel-button-alt w-1/2"
                        onClick={handleCaptureComplete}
                      >
                        촬영
                      </button>
                    </div>
                  </div>
                </div>
              )}
              
              {verificationStatus === VerificationStatus.SUBMITTED && (
                <div className="fixed inset-0 bg-black bg-opacity-70 flex items-center justify-center p-4 z-50">
                  <div className="bg-white rounded-lg p-6 w-full max-w-md text-center">
                    <h3 className="font-pixel text-xl mb-2">✅ 제출 완료!</h3>
                    <p>인증 사진이 접수되었습니다</p>
                    <div className="mt-4">
                      <button 
                        className="pixel-button-alt"
                        onClick={() => setVerificationStatus(VerificationStatus.PENDING)}
                      >
                        확인
                      </button>
                    </div>
                  </div>
                </div>
              )}
            </div>
          )}
          
          {/* Party tab */}
          {activeTab === "party" && (
            <div>
              <h3 className="font-pixel text-lg mb-2">👥 파티원 ({party?.members.length}/{dungeon.maxPartySize})</h3>
              <div className="space-y-2">
                {party?.members.map(member => (
                  <PartyMember 
                    key={member.id} 
                    member={member} 
                    isLeader={party.leaderId === member.id}
                  />
                ))}
              </div>
              
              {/* Join party button */}
              {party && party.members.length < dungeon.maxPartySize && (
                <button className="pixel-button w-full mt-4">
                  파티에 참가하기
                </button>
              )}
              
              {/* Required classes */}
              <div className="retro-border bg-white mt-4">
                <h3 className="font-pixel text-sm mb-2">필요한 직업군</h3>
                <div className="space-y-1">
                  <div className="flex justify-between items-center">
                    <div className="text-sm">🗡️ 전사 (ESTJ, ESTP)</div>
                    <div className="bg-gray-200 px-2 py-0.5 rounded text-xs">2/3</div>
                  </div>
                  <div className="flex justify-between items-center">
                    <div className="text-sm">🧙‍♂️ 마법사 (INTJ, INTP)</div>
                    <div className="bg-gray-200 px-2 py-0.5 rounded text-xs">1/1</div>
                  </div>
                  <div className="flex justify-between items-center">
                    <div className="text-sm">💊 힐러 (ISFJ, ESFJ)</div>
                    <div className="bg-gray-200 px-2 py-0.5 rounded text-xs">1/2</div>
                  </div>
                  <div className="flex justify-between items-center">
                    <div className="text-sm">🏹 레인저 (ISTP, ENFP)</div>
                    <div className="bg-gray-200 px-2 py-0.5 rounded text-xs">0/1</div>
                  </div>
                </div>
              </div>
            </div>
          )}
          
          {/* Chat tab */}
          {activeTab === "chat" && (
            <div>
              <h3 className="font-pixel text-lg mb-2">💬 파티 채팅</h3>
              <div className="retro-border bg-white h-[320px] flex flex-col">
                {/* Chat messages would go here */}
                <div className="flex-grow p-3 overflow-y-auto">
                  <div className="mb-2">
                    <div className="text-xs text-gray-500">용사쿤</div>
                    <div className="bg-gray-100 p-2 rounded-lg inline-block">
                      안녕하세요!!
                    </div>
                  </div>
                  
                  <div className="mb-2">
                    <div className="text-xs text-gray-500">마법사짱</div>
                    <div className="bg-gray-100 p-2 rounded-lg inline-block">
                      일정 조율할게요~
                    </div>
                  </div>
                </div>
                
                {/* Chat input */}
                <div className="border-t p-3">
                  <div className="flex">
                    <input
                      type="text"
                      placeholder="메시지를 입력하세요."
                      className="border border-gray-300 rounded-md p-2 flex-grow mr-2"
                    />
                    <button className="pixel-button-alt">
                      전송
                    </button>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      </main>

      <Footer />
    </div>
  );
};

export default DungeonDetail;
