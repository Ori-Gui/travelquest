import { defineStore } from 'pinia'
import axios from '@/lib/axios'

export const useDungeonStore = defineStore('dungeon', {
  state: () => ({
    dungeon: null,
    attractions: [],
    loadedFor: null
  }),
  actions: {
    async load(dungeonId) {
      console.log("호출")
      const [{ data: d }, { data: at }] = await Promise.all([
        axios.get(`/api/v1/dungeons/${dungeonId}`),
        axios.get(`/api/v1/dungeons/${dungeonId}/attractions`)
      ])

      this.dungeon      = d
      this.attractions  = at
      this.loadedFor    = dungeonId
    }
  }
})
