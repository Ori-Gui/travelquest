
import { Quest } from "@/types";

interface QuestCardProps {
  quest: Quest;
  index: number;
  onVerify?: (questId: string) => void;
}

const QuestCard: React.FC<QuestCardProps> = ({ quest, index, onVerify }) => {
  return (
    <div className="retro-border bg-white mb-4">
      <div className="font-pixel">
        <h3 className="text-lg font-bold mb-1">Quest {index + 1}: {quest.title}</h3>
        <p className="text-sm mb-3">{quest.description}</p>
        
        {quest.verificationNeeded && (
          <button 
            onClick={() => onVerify && onVerify(quest.id)}
            className={`pixel-button-alt w-full ${quest.completed ? 'bg-gray-400' : ''}`}
            disabled={quest.completed}
          >
            {quest.completed ? '완료됨' : '인증 사진찍기'}
          </button>
        )}
        
        {quest.completed && (
          <div className="mt-2 text-center text-green-600">
            ✓ 완료됨
          </div>
        )}
      </div>
    </div>
  );
};

export default QuestCard;
