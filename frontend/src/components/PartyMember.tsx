
import { User } from "@/types";

interface PartyMemberProps {
  member: User;
  isLeader?: boolean;
}

const PartyMember: React.FC<PartyMemberProps> = ({ member, isLeader }) => {
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
        return 'text-gray-600';
    }
  };

  const getClassIcon = (userClass: string) => {
    switch (userClass) {
      case 'warrior':
        return '⚔️';
      case 'mage':
        return '🔮';
      case 'healer':
        return '🧪';
      case 'ranger':
        return '🏹';
      default:
        return '👤';
    }
  };

  const classColor = getClassColor(member.class);

  return (
    <div className="flex items-center space-x-3 p-2 border border-gray-200 rounded-md">
      <div className="w-10 h-10 bg-gray-300 rounded-full flex items-center justify-center">
        {getClassIcon(member.class)}
      </div>
      <div>
        <div className="flex items-center">
          <span className="font-medium">{member.username}</span>
          {isLeader && (
            <span className="ml-2 text-xs bg-quest-yellow text-black px-2 py-0.5 rounded-full">
              파티장
            </span>
          )}
        </div>
        <div className="flex items-center text-sm">
          <span className={`${classColor} font-medium`}>
            {member.class.charAt(0).toUpperCase() + member.class.slice(1)}
          </span>
          <span className="mx-1">•</span>
          <span>Lv.{member.level}</span>
          <span className="mx-1">•</span>
          <span>{member.mbti}</span>
        </div>
      </div>
    </div>
  );
};

export default PartyMember;
