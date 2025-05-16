
import { Link } from "react-router-dom";
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import { mockCurrentUser, mockDungeons } from "@/data/mockData";

const Index = () => {
  const currentDungeon = mockDungeons[0];
  const formatDate = (dateStr: string) => {
    const options: Intl.DateTimeFormatOptions = { month: 'numeric', day: 'numeric' };
    const date = new Date(dateStr);
    return date.toLocaleDateString('ko-KR', options);
  };

  return (
    <div className="min-h-screen flex flex-col">
      <Header />

      <main className="flex-grow p-4">
        {/* Trip information */}
        <div className="retro-border bg-white mb-6">
          <p className="text-sm">
            🗓️ {formatDate(currentDungeon.startDate)} ~ {formatDate(currentDungeon.endDate)} | No.{currentDungeon.id} | {currentDungeon.title}
          </p>
          <p className="text-xs text-gray-600">
            테마: {currentDungeon.tags.join(', ')}
          </p>
        </div>

        {/* Main buttons */}
        <div className="grid grid-cols-3 gap-2 mb-4">
          <Link to="/hostels" className="pixel-button text-sm">숙소 예약하기</Link>
          <Link to="/transportation" className="pixel-button text-sm">교통편 예약하기</Link>
          <Link to="/trip-planner" className="pixel-button text-sm">여행 계획 짜기</Link>
        </div>

        {/* Action buttons */}
        <div className="flex space-x-2 mb-6">
          <Link to="/chat/1" className="pixel-button-danger text-sm">
            채팅하기
          </Link>
          <Link to="/party/1" className="pixel-button-danger text-sm">
            파티 나가기
          </Link>
        </div>

        {/* AI Quest section */}
        <div className="mb-6">
          <h2 className="font-pixel text-lg mb-2">🤖 AI 생성 퀘스트</h2>
          <div className="retro-border bg-white">
            <ul className="space-y-2">
              <li className="flex items-baseline">
                <span className="font-pixel text-purple-600">Quest 1:</span>
                <span className="ml-2 text-sm">강원평창에서 단체사진 찍기</span>
              </li>
              <li className="flex items-baseline">
                <span className="font-pixel text-purple-600">Quest 2:</span>
                <span className="ml-2 text-sm">지역 맛집 투어 진행</span>
              </li>
              <li className="flex items-baseline">
                <span className="font-pixel text-purple-600">Quest 3:</span>
                <span className="ml-2 text-sm">지도에 없는 명소 찾기</span>
              </li>
            </ul>
            <div className="mt-3">
              <Link to="/quests/1" className="font-pixel text-sm text-purple-700 border border-purple-700 rounded-md px-3 py-1">
                인증글 작성하기
              </Link>
            </div>
          </div>
          <p className="text-xs text-gray-500 mt-1 text-center">⭐ 코일지기 리워드를 주는 미션들을 완료해보세요.</p>
        </div>

        {/* Danger alert */}
        <div className="mt-10 mb-2">
          <div className="text-center text-red-600 font-pixel text-xl">🚨 경고!</div>
          <div className="text-center text-sm">탑승을 시작하면 모두에게 알림이 전달됩니다.</div>
          <div className="mt-4 text-center">
            <button className="bg-red-600 text-white font-pixel px-8 py-2 rounded-md border-2 border-black">
              탑승 시작하기
            </button>
          </div>
          <p className="text-xs text-center mt-2">* 당일이 지나면면 코일보상과 참가자 정보가 공개됩니다.</p>
        </div>
      </main>

      <Footer />
    </div>
  );
};

export default Index;
