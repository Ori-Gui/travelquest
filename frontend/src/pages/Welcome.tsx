
import { Link } from "react-router-dom";
import Header from "@/components/Header";
import { useEffect } from "react";

const Welcome = () => {
  const pixelWarrior = "/lovable-uploads/97ce16d5-9bd5-4365-9e62-18f1d9a44e13.png";

  return (
    <div className="min-h-screen flex flex-col">
      <Header />

      <main className="flex-grow p-4 flex items-center justify-center">
        <div className="retro-border bg-white w-full max-w-md text-center">
          <h1 className="font-pixel text-xl mb-4">
            환영해 낯, Travel Quest에 오신 것을 환영합니다!
          </h1>

          <div className="mb-4 animate-pixel-bounce">
            <img 
              src={pixelWarrior} 
              alt="Pixel Warrior" 
              className="inline-block w-32 h-auto"
            />
          </div>

          <p className="mb-4">
            당신의 직업은 <span className="font-pixel text-lg text-quest-purple">"전사"</span>입니다!
          </p>
          
          <Link to="/" className="pixel-button-alt w-[200px] inline-flex justify-center">
            👉 시작하러 가기
          </Link>
        </div>
      </main>

      <footer className="p-4 text-center text-xs text-gray-500">
        © 2025 Travel Quest. All rights reserved.
      </footer>
    </div>
  );
};

export default Welcome;
