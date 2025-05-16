
import { Link } from "react-router-dom";

const Footer = () => {
  return (
    <footer className="mt-auto p-4 text-center text-sm border-t border-gray-200">
      <div className="flex justify-center space-x-12">
        <Link to="/" className="flex flex-col items-center">
          <span className="text-xs">홈</span>
        </Link>
        <Link to="/dungeons" className="flex flex-col items-center">
          <span className="text-xs">던전 찾기</span>
        </Link>
        <Link to="/parties" className="flex flex-col items-center">
          <span className="text-xs">파티</span>
        </Link>
        <Link to="/profile" className="flex flex-col items-center">
          <span className="text-xs">마이페이지</span>
        </Link>
      </div>
      <p className="mt-4">© 2025 Travel Quest. All rights reserved.</p>
    </footer>
  );
};

export default Footer;
