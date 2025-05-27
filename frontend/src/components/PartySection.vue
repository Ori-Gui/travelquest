<template>
  <section class="party-info" v-if="partyMembers && partyMembers.length">
    <div class="party-members">
      <div v-for="member in partyMembers" :key="member.id" class="member-card">
        <div class="avatar">{{ getJobEmoji(member.job) }}</div>
        <div class="info">
          <div class="name">
            {{ member.nickname }}
            <span class="role" v-if="member.leader">(리더)</span>
          </div>
          <div class="meta">{{ member.jobName }} • {{ member.mbti }}</div>
        </div>
        <!-- Kick button for leader to remove other members -->
        <button
          v-if="isCurrentUserLeader && !member.leader"
          class="kick-button"
          @click="kickMember(member.id)"
        >
          X
        </button>
      </div>
    </div>

    <button
      class="join-button"
      v-if="status === 'MATCHING' && currentUserId && partyMembers.length > 0 && !isAlreadyMember"
      @click="joinPartyEvent"
    >
      파티에 참가하기
    </button>

    <button
      class="leave-button"
      v-if="status === 'MATCHING' && currentUserId && partyMembers.length > 0 && isAlreadyMember"
      @click="leavePartyEvent"
    >
      파티 떠나기
    </button>

    <div class="required-jobs" v-if="requiredJobs && requiredJobs.length">
      <div>필요한 직업군</div>
      <ul>
        <li v-for="job in requiredJobs" :key="job.job">
          {{ getJobEmoji(job.job) }} {{ job.name }} ({{ job.mbtiList.join(', ') }})
        </li>
      </ul>
    </div>
  </section>
</template>

<script setup>
import { ref, computed, watchEffect } from 'vue';
import {
  getPartyStatus,
  getPartyMembers,
  getRequiredJobs,
  joinParty,
  leaveParty,
  kickPartyMember
} from '@/api/party';
import { useUserStore } from '@/stores/userStore';

const props = defineProps({
  partyId: {
    type: Number,
    required: true
  }
});

const userStore = useUserStore();
const currentUserId = computed(() => userStore.user?.id);

const partyMembers = ref([]);
const requiredJobs = ref([]);
const status = ref('');

// Check if current user is the party leader
const isCurrentUserLeader = computed(() => {
  return partyMembers.value.some(
    member => String(member.id) === String(currentUserId.value) && member.leader
  );
});

const jobEmojiMap = {
  WARRIOR: '🛡️',
  MAGE: '🪄',
  HEALER: '💉',
  RANGER: '🏹',
  BARD: '🎵',
  TRICKSTER: '🃏',
  THIEF: '🗡️',
  MECHANIC: '🔧'
};

function getJobEmoji(jobCode) {
  return jobEmojiMap[jobCode] || '🎯';
}

const isAlreadyMember = computed(() => {
  return partyMembers.value.some(
    member => String(member.id) === String(currentUserId.value)
  );
});

watchEffect(async () => {
  if (!props.partyId || !userStore.user?.id) {
    console.log('⏳ user not ready yet');
    return;
  }

  try {
    const [info, members, jobs] = await Promise.all([
      getPartyStatus(props.partyId),
      getPartyMembers(props.partyId),
      getRequiredJobs(props.partyId)
    ]);
    status.value = info.status;
    // sort members: leader on top
    const sorted = members.sort((a, b) => {
      if (a.leader && !b.leader) return -1;
      if (!a.leader && b.leader) return 1;
      return 0;
    });
    partyMembers.value = sorted;
    requiredJobs.value = jobs;
  } catch (e) {
    console.error('파티 정보 불러오기 오류:', e);
    partyMembers.value = [];
    requiredJobs.value = [];
    status.value = 'CLOSED';
  }
});

async function joinPartyEvent() {
  try {
    await joinParty(props.partyId);
    window.location.reload();
  } catch (error) {
    console.error('파티 참가 실패:', error);
    alert('파티 참가에 실패했습니다.');
  }
}

async function leavePartyEvent() {
  try {
    await leaveParty(props.partyId);
    window.location.reload();
  } catch (error) {
    console.error('파티 떠나기 실패:', error);
    alert('파티 떠나기에 실패했습니다.');
  }
  // stompClient.publish({
  //   destination: `/ws/chat.leave.${props.partyId}`,
  //   body: JSON.stringify({
  //     userId: currentUserId.value,
  //     name: userStore.user?.nickname,
  //     system: true,
  //     content: `${userStore.user?.nickname}님이 파티에서 나가셨습니다.`
  //   })
  // });
}

// Kick member function for leader
async function kickMember(memberId) {
  try {
    await kickPartyMember(props.partyId, memberId);
    const members = await getPartyMembers(props.partyId);
    // sort after kick
    const sorted = members.sort((a, b) => {
      if (a.leader && !b.leader) return -1;
      if (!a.leader && b.leader) return 1;
      return 0;
    });
    partyMembers.value = sorted;
  } catch (error) {
    console.error('강퇴 실패:', error);
    alert('멤버 강퇴에 실패했습니다.');
  }
}
</script>

<style scoped>
.party-info {
  margin-top: 1rem;
}

.party-members {
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  margin-bottom: 1rem;
}

.member-card {
  display: flex;
  align-items: center;
  background-color: #f0fff0;
  border: 1px solid #a0d8a0;
  border-radius: 10px;
  padding: 0.6rem;
  box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
  position: relative;
}

.member-card .avatar {
  font-size: 2rem;
  margin-right: 1rem;
}

.member-card .info .name {
  font-weight: bold;
  font-size: 1.1rem;
}

.member-card .info .role {
  font-size: 0.9rem;
  color: #666;
}

.member-card .info .meta {
  font-size: 0.9rem;
  color: #333;
}

.kick-button {
  margin-left: auto;
  background: transparent;
  border: none;
  font-size: 1.5rem;
  padding: 0.25rem 0.5rem;
  width: 2rem;
  height: 2rem;
  line-height: 1;
  text-align: center;
  cursor: pointer;
  color: #ff0000;
}

.join-button {
  width: 100%;
  background-color: yellow;
  padding: 0.5rem;
  border: 1px solid black;
  font-weight: bold;
  margin: 1rem 0;
}

.leave-button {
  width: 100%;
  background-color: #ff6347;
  padding: 0.5rem;
  border: 1px solid black;
  font-weight: bold;
  margin: 1rem 0;
}

.required-jobs {
  background-color: #fff;
  border: 2px solid #a0d8a0;
  padding: 1rem;
  border-radius: 12px;
  box-shadow: 1px 1px 4px rgba(0, 0, 0, 0.05);
  margin-top: 1rem;
}

.required-jobs > div {
  font-size: 1.1rem;
  font-weight: bold;
  margin-bottom: 0.5rem;
  color: #2d5f2e;
}

.required-jobs ul {
  list-style: none;
  padding: 0;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 0.5rem;
}

.required-jobs li {
  display: flex;
  align-items: center;
  gap: 0.4rem;
  background-color: #f0fff0;
  border: 1px solid #c3eec3;
  padding: 0.5rem 0.75rem;
  border-radius: 8px;
  font-size: 0.9rem;
  color: #3b633c;
  box-shadow: inset 0 1px 2px rgba(0,0,0,0.03);
}
</style>
