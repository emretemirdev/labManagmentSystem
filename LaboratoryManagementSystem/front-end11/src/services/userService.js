// services/userService.js
import axios from 'axios';
import jwtDecode from 'jwt-decode';

axios.defaults.baseURL = 'http://localhost:8080';

export const getUserInfo = async () => {
  try {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken = jwtDecode(token);
      const userId = decodedToken.userId;
      
      const response = await axios.get(`/auth/getUserInfo/${userId}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      return response.data;
    } else {
      throw new Error('Kullanıcı girişi yapılmamış.');
    }
  } catch (error) {
    console.error('Kullanıcı bilgileri alınırken bir hata oluştu', error);
    throw error;
  }
};
