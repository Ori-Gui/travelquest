
import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Index from "./pages/Index";
import Profile from "./pages/Profile";
import DungeonList from "./pages/DungeonList";
import DungeonDetail from "./pages/DungeonDetail";
import CreateDungeon from "./pages/CreateDungeon";
import PartyList from "./pages/PartyList";
import Chat from "./pages/Chat";
import Login from "./pages/Login";
import Welcome from "./pages/Welcome";
import NotFound from "./pages/NotFound";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <Toaster />
      <Sonner />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Index />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/dungeons" element={<DungeonList />} />
          <Route path="/dungeons/:id" element={<DungeonDetail />} />
          <Route path="/dungeons/create" element={<CreateDungeon />} />
          <Route path="/parties" element={<PartyList />} />
          <Route path="/chat/:roomId" element={<Chat />} />
          <Route path="/login" element={<Login />} />
          <Route path="/welcome" element={<Welcome />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;
