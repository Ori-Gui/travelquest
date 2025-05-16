
import { Dungeon } from "@/types";
import { Link } from "react-router-dom";

interface DungeonCardProps {
  dungeon: Dungeon;
}

const DungeonCard: React.FC<DungeonCardProps> = ({ dungeon }) => {
  const formatDate = (dateString: string) => {
    const options: Intl.DateTimeFormatOptions = { month: 'numeric', day: 'numeric' };
    return new Date(dateString).toLocaleDateString('ko-KR', options);
  };

  return (
    <Link to={`/dungeons/${dungeon.id}`}>
      <div className="retro-border bg-white mb-4 hover:shadow-[6px_6px_0px_0px_rgba(0,0,0,1)] transition-shadow">
        <div className="font-pixel">
          <div className="flex justify-between items-start">
            <div>
              <h3 className="text-lg font-bold">No.{dungeon.id}</h3>
              <p className="text-sm">제목: {dungeon.title}</p>
              <p className="text-sm">파티원: {dungeon.currentPartySize}/{dungeon.maxPartySize}</p>
            </div>
            <div className="flex space-x-2">
              <button className="bg-quest-dark-green text-white px-3 py-1 rounded text-sm">참가</button>
              <button className="bg-quest-pink text-white px-3 py-1 rounded text-sm">삭제</button>
            </div>
          </div>
          <div className="flex mt-2">
            {dungeon.tags.map((tag, index) => (
              <span key={index} className="text-xs bg-gray-100 rounded-full px-2 py-0.5 mr-1">
                {tag}
              </span>
            ))}
          </div>
          <div className="mt-3">
            <span className="text-sm">
              🗓️ {formatDate(dungeon.startDate)} ~ {formatDate(dungeon.endDate)}
            </span>
          </div>
        </div>
      </div>
    </Link>
  );
};

export default DungeonCard;
