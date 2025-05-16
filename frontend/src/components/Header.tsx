
import { Menu } from "lucide-react";
import { useState } from "react";
import { Link } from "react-router-dom";

const Header = () => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <header className="bg-quest-green border-b-2 border-black px-4 py-3">
      <div className="container mx-auto flex justify-between items-center">
        <Link to="/" className="font-pixel text-2xl tracking-wider">Travel Quest</Link>
        
        <button 
          className="border-2 border-black rounded-md p-1"
          onClick={() => setIsOpen(!isOpen)}
          aria-label="Toggle menu"
        >
          <Menu size={24} />
        </button>
        
        {isOpen && (
          <div className="absolute right-4 top-16 bg-white border-2 border-black rounded-md shadow-[4px_4px_0px_0px_rgba(0,0,0,1)] z-50 p-4">
            <ul className="space-y-2">
              <li><Link to="/" className="block py-1" onClick={() => setIsOpen(false)}>홈</Link></li>
              <li><Link to="/profile" className="block py-1" onClick={() => setIsOpen(false)}>프로필</Link></li>
              <li><Link to="/dungeons" className="block py-1" onClick={() => setIsOpen(false)}>던전 목록</Link></li>
              <li><Link to="/parties" className="block py-1" onClick={() => setIsOpen(false)}>내 파티</Link></li>
            </ul>
          </div>
        )}
      </div>
    </header>
  );
};

export default Header;
