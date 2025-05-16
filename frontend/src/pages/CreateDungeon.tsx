
import { useState } from "react";
import Header from "@/components/Header";
import { Link } from "react-router-dom";

const CreateDungeon = () => {
  const [title, setTitle] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [maxPartySize, setMaxPartySize] = useState("5");
  const [description, setDescription] = useState("");
  const [selectedTags, setSelectedTags] = useState<string[]>([]);
  
  const availableTags = [
    "자연", "식도락", "사진", "역사", "문화", "쇼핑", 
    "하이킹", "바다", "산", "명소", "도시"
  ];

  const handleTagToggle = (tag: string) => {
    if (selectedTags.includes(tag)) {
      setSelectedTags(selectedTags.filter(t => t !== tag));
    } else {
      setSelectedTags([...selectedTags, tag]);
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Submit logic would go here
    console.log({ title, startDate, endDate, maxPartySize, description, selectedTags });
  };

  return (
    <div className="min-h-screen flex flex-col">
      <Header />

      <main className="flex-grow p-4">
        <div className="retro-border bg-white mb-6">
          <h2 className="font-pixel text-xl mb-4 text-center">
            🗺️ 새로운 던전 생성
          </h2>

          <form onSubmit={handleSubmit}>
            <div className="mb-4">
              <label className="block mb-1">제목</label>
              <input 
                type="text" 
                className="w-full border-2 border-black rounded-md p-2" 
                placeholder="던전의 제목을 입력하세요"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
              />
            </div>
            
            <div className="mb-4">
              <label className="block mb-1">시작 날짜</label>
              <input 
                type="date" 
                className="w-full border-2 border-black rounded-md p-2" 
                value={startDate}
                onChange={(e) => setStartDate(e.target.value)}
                required
              />
            </div>
            
            <div className="mb-4">
              <label className="block mb-1">종료 날짜</label>
              <input 
                type="date" 
                className="w-full border-2 border-black rounded-md p-2" 
                value={endDate}
                onChange={(e) => setEndDate(e.target.value)}
                required
              />
            </div>
            
            <div className="mb-4">
              <label className="block mb-1">최대 인원수</label>
              <select
                className="w-full border-2 border-black rounded-md p-2"
                value={maxPartySize}
                onChange={(e) => setMaxPartySize(e.target.value)}
                required
              >
                <option value="2">2명</option>
                <option value="3">3명</option>
                <option value="4">4명</option>
                <option value="5">5명</option>
                <option value="6">6명</option>
                <option value="8">8명</option>
                <option value="10">10명</option>
              </select>
            </div>
            
            <div className="mb-4">
              <label className="block mb-1">설명</label>
              <textarea
                className="w-full border-2 border-black rounded-md p-2 h-24" 
                placeholder="던전에 대한 설명을 입력하세요"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              ></textarea>
            </div>
            
            <div className="mb-6">
              <label className="block mb-1">태그 선택</label>
              <div className="flex flex-wrap gap-2">
                {availableTags.map(tag => (
                  <button
                    type="button"
                    key={tag}
                    onClick={() => handleTagToggle(tag)}
                    className={`px-3 py-1 text-sm rounded-full ${
                      selectedTags.includes(tag) 
                        ? 'bg-quest-dark-green text-white' 
                        : 'bg-gray-200'
                    }`}
                  >
                    {tag}
                  </button>
                ))}
              </div>
            </div>
            
            <div className="flex space-x-2">
              <Link to="/dungeons" className="pixel-button-danger w-full">
                취소
              </Link>
              <button type="submit" className="pixel-button-alt w-full">
                등록하기
              </button>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
};

export default CreateDungeon;
