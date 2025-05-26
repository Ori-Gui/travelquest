// lib/authAxios.js
import axios from 'axios'
export default axios.create({
  baseURL: 'http://192.168.205.51:8080',
  withCredentials: true
})
