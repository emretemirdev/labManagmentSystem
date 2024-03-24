import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // useNavigate'i import edin


function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate(); // useNavigate hook'unu kullanın
  
    const handleLogin = async (e) => {
      e.preventDefault();
      try {
        const response = await axios.post('http://localhost:8080/auth/generateToken', {
          username,
          password,
        });
        
        // Login başarılı, token'ı saklayın
        const token = response.data;
        localStorage.setItem('token', token); // Token'ı localStorage'e saklayın
        
        setError(''); // Hataları temizleyin
        navigate('/reports'); // '/reports' yoluna yönlendirin

    } catch (error) {
      if (error.response) {
        // Sunucu tarafından dönen hata (5xx hata kodları, 4xx hata kodları vb.)
        console.log(error.response.data);
        setError('Login failed: ' + error.response.data.message);
      } else if (error.request) {
        // İstek yapıldı, ama hiçbir yanıt alınamadı
        console.log(error.request);
        setError('No response from server');
      } else {
        // İstek yapılırken bir hata oluştu
        console.log('Error', error.message);
        setError('Error: ' + error.message);
      }
    }
  };

  return (
    <div>
      <form onSubmit={handleLogin}>
        <label>
          Username:
          <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
        </label>
        <label>
          Password:
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
        </label>
        <button type="submit">Login</button>
      </form>
      {error && <p>{error}</p>}
    </div>
  );
}

export default Login;