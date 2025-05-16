import { useState } from "react";
import { Link } from "react-router-dom";
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import { mockDungeons } from "@/data/mockData";
import DungeonCard from "@/components/DungeonCard";

const tabs = [
  { id: "available", label: "모집중" },
  { id: "joined", label: "참여 완료" },
  { id: "my", label: "내가 만든 던전" }
];

const DungeonList = () => {
  const [activeTab, setActiveTab] = useState("available");
  const [dateRange, setDateRange] = useState("");
  
  const filteredDungeons = mockDungeons.filter(dungeon => {
    // Logic for filtering dungeons based on active tab
    if (activeTab === "available") {
      return dungeon.currentPartySize < dungeon.maxPartySize;
    }
    
    // Other filter conditions could be added for other tabs
    return true;
  });

  return (
    <div className="min-h-screen flex flex-col">
      <Header />

      <main className="flex-grow p-4">
        {/* Search filters */}
        <div className="mb-4">
          <div className="mb-2">
            <input 
              type="date" 
              className="w-full border-2 border-black rounded-md p-2" 
              value={dateRange}
              onChange={(e) => setDateRange(e.target.value)}
            />
          </div>
          
          <div className="flex space-x-2">
            <select className="border-2 border-black rounded-md p-2 flex-1">
              <option>활동지역</option>
              <option>서울</option>
              <option>부산</option>
              <option>제주</option>
            </select>
            
            <select className="border-2 border-black rounded-md p-2 flex-1">
              <option>주요활동 테마</option>
              <option>맛집</option>
              <option>관광</option>
              <option>힐링</option>
            </select>
            
            <button className="pixel-button-alt">
              던전찾기
            </button>
          </div>
        </div>
        
        {/* Map preview (placeholder) */}
        <div className="retro-border bg-white h-[200px] mb-4 flex items-center justify-center">
          <div className="text-center">
            <div className="text-gray-400">지도 표시 영역</div>
            <p className="text-sm text-gray-500">Level: 4-6 던전이 표시됩니다</p>
          </div>
        </div>
        
        {/* Tabs */}
        <div className="flex border-b mb-4">
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
        
        {/* Dungeon list */}
        <div>
          {filteredDungeons.length > 0 ? (
            filteredDungeons.map(dungeon => (
              <DungeonCard key={dungeon.id} dungeon={dungeon} />
            ))
          ) : (
            <div className="text-center py-8 text-gray-500">
              표시할 던전이 없습니다
            </div>
          )}
        </div>
        
        {/* Add dungeon button */}
        <div className="fixed bottom-20 right-4">
          <Link to="/dungeons/create" className="pixel-button-alt rounded-full w-12 h-12 flex items-center justify-center text-xl">
            +
          </Link>
        </div>
      </main>

      <Footer />
    </div>
  );
};

export default DungeonList;
