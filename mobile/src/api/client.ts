import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000,
  headers: {
    'X-User-Id': '1',
    'X-Account-Id': '1',
  },
});
