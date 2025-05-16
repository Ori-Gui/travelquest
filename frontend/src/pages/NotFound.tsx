
import { useLocation } from "react-router-dom";
import { useEffect } from "react";
import { Link } from "react-router-dom";

const NotFound = () => {
  const location = useLocation();

  useEffect(() => {
    console.error(
      "404 Error: User attempted to access non-existent route:",
      location.pathname
    );
  }, [location.pathname]);

  return (
    <div className="min-h-screen flex items-center justify-center bg-quest-green p-4">
      <div className="retro-border bg-white text-center w-full max-w-md">
        <h1 className="font-pixel text-4xl mb-4">404</h1>
        <p className="text-xl text-gray-600 mb-6">던전을 찾을 수 없어요!</p>
        <p className="mb-6">모험가님이 찾으시는 페이지가 존재하지 않거나, 이동되었어요.</p>
        <Link to="/" className="pixel-button inline-flex">
          홈으로 돌아가기
        </Link>
      </div>
    </div>
  );
};

export default NotFound;
