
import { useParams, useNavigate } from "react-router-dom";
import Header from "@/components/Header";
import { useState, useEffect, useRef } from "react";
import { mockChatMessages, mockCurrentUser, mockParties } from "@/data/mockData";
import { ChatMessage } from "@/types";

const Chat = () => {
  const { roomId } = useParams<{ roomId: string }>();
  const navigate = useNavigate();
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const [newMessage, setNewMessage] = useState("");
  const messagesEndRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    // Load mock chat messages
    setMessages(mockChatMessages);
  }, [roomId]);

  useEffect(() => {
    // Scroll to bottom when messages change
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  const handleSendMessage = (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!newMessage.trim()) return;
    
    const currentTime = new Date().toISOString();
    
    const message: ChatMessage = {
      id: `msg-${Date.now()}`,
      roomId: roomId || "1",
      userId: mockCurrentUser.id,
      username: mockCurrentUser.username,
      message: newMessage,
      sentAt: currentTime
    };
    
    setMessages([...messages, message]);
    setNewMessage("");
  };

  const formatTime = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  };

  if (!roomId) {
    return (
      <div className="min-h-screen flex flex-col">
        <Header />
        <main className="flex-grow p-4 flex items-center justify-center">
          <div className="text-center">
            <h2 className="font-pixel text-xl mb-2">채팅방을 찾을 수 없습니다</h2>
            <p>유효한 채팅방 ID가 필요합니다.</p>
          </div>
        </main>
      </div>
    );
  }

  const party = mockParties.find(p => p.id === roomId);

  return (
    <div className="min-h-screen flex flex-col">
      <Header />

      <main className="flex-grow flex flex-col p-4">
        <div className="mb-4 flex items-center">
          <button 
            className="mr-3"
            onClick={() => navigate(-1)}
          >
            ←
          </button>
          <h2 className="font-pixel text-lg">
            {party ? `${party.members.length}명의 파티원` : '채팅방'} <span className="text-sm">5/5</span>
          </h2>
        </div>

        <div className="flex-grow retro-border bg-white overflow-hidden flex flex-col">
          <div className="flex-grow overflow-y-auto p-3">
            {messages.map((msg) => (
              <div
                key={msg.id}
                className={`mb-2 ${
                  msg.userId === mockCurrentUser.id
                    ? "text-right"
                    : "text-left"
                }`}
              >
                {msg.userId !== mockCurrentUser.id && (
                  <div className="text-xs text-gray-600">{msg.username}</div>
                )}
                <div className="inline-block max-w-[80%]">
                  <div
                    className={`rounded-lg px-3 py-2 break-words ${
                      msg.userId === mockCurrentUser.id
                        ? "bg-quest-dark-green text-white"
                        : "bg-gray-200"
                    }`}
                  >
                    {msg.message}
                  </div>
                  <div className="text-xs text-gray-500 mt-1">
                    {formatTime(msg.sentAt)}
                  </div>
                </div>
              </div>
            ))}
            <div ref={messagesEndRef} />
          </div>

          <div className="border-t p-3">
            <form onSubmit={handleSendMessage} className="flex">
              <input
                type="text"
                className="flex-grow border-2 border-black rounded-md p-2 mr-2"
                placeholder="메시지를 입력하세요."
                value={newMessage}
                onChange={(e) => setNewMessage(e.target.value)}
              />
              <button
                type="submit"
                className="pixel-button-alt"
              >
                전송
              </button>
            </form>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Chat;
