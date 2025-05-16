
import { Link } from "react-router-dom";
import { useState } from "react";
import Header from "@/components/Header";

const Login = () => {
  const [isLogin, setIsLogin] = useState(true);

  return (
    <div className="min-h-screen flex flex-col">
      <Header />

      <main className="flex-grow flex items-center justify-center p-4">
        <div className="w-full max-w-md">
          <div className="retro-border bg-white">
            <h1 className="font-pixel text-2xl text-center text-quest-dark-green mb-6">
              Travel Quest
            </h1>

            {isLogin ? (
              <div className="mb-8">
                <Link 
                  to="/welcome"
                  className="pixel-button w-full mb-3 flex justify-center items-center"
                >
                  👑 카카오 로그인
                </Link>
                
                <Link 
                  to="/welcome"
                  className="pixel-button-alt w-full flex justify-center items-center"
                >
                  🔒 네이버 로그인
                </Link>
              </div>
            ) : (
              <div>
                <div className="mb-4">
                  <label className="block mb-1">아이디</label>
                  <input 
                    type="text" 
                    className="w-full border-2 border-black rounded-md p-2" 
                    placeholder="아이디"
                  />
                </div>
                <div className="mb-4">
                  <label className="block mb-1">닉네임</label>
                  <input 
                    type="text" 
                    className="w-full border-2 border-black rounded-md p-2" 
                    placeholder="닉네임"
                  />
                </div>
                <div className="mb-4">
                  <label className="block mb-1">성별</label>
                  <input 
                    type="text" 
                    className="w-full border-2 border-black rounded-md p-2" 
                    placeholder="성별"
                  />
                </div>
                <div className="mb-4">
                  <label className="block mb-1">연도-월-일</label>
                  <input 
                    type="date" 
                    className="w-full border-2 border-black rounded-md p-2"
                  />
                </div>
                <button 
                  className="pixel-button-alt w-full"
                >
                  회원 가입
                </button>
              </div>
            )}
            
            <div className="text-center mt-4">
              <button
                onClick={() => setIsLogin(!isLogin)}
                className="text-sm text-gray-500 underline"
              >
                {isLogin ? '회원가입 하기' : '로그인 하기'}
              </button>
            </div>
          </div>
          
          <p className="text-center text-xs mt-4 text-gray-500">
            © 2025 Travel Quest. All rights reserved.
          </p>
        </div>
      </main>
    </div>
  );
};

export default Login;
