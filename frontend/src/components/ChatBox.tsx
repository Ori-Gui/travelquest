
import { ChatMessage } from "@/types";
import { useState, useEffect, useRef } from "react";
import { mockChatMessages, mockCurrentUser } from "@/data/mockData";

interface ChatBoxProps {
  roomId: string;
}

const ChatBox: React.FC<ChatBoxProps> = ({ roomId }) => {
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const [newMessage, setNewMessage] = useState("");
  const messagesEndRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    // Load initial messages
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
      roomId,
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

  return (
    <div className="flex flex-col h-[400px] retro-border bg-white">
      <div className="flex-1 overflow-auto p-2 mb-2">
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

      <form onSubmit={handleSendMessage} className="border-t pt-2 px-2">
        <div className="flex">
          <input
            type="text"
            className="flex-1 border border-black rounded-md px-3 py-1 mr-2"
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
        </div>
      </form>
    </div>
  );
};

export default ChatBox;
