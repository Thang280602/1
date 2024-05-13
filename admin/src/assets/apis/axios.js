import axios from 'axios';

const instance = axios.create({
    baseURL: import.meta.env.VITE_URL_BACKEND,
    "Accept-Language": "vi"
});

export default instance;
