import React, { useState } from 'react';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

function ReportForm() {
  const [report, setReport] = useState({
    name: '',
    surName: '',
    identifyNumber: '',
    diagnosisTitle: '',
    diagnosisInfo: '',
    reportDate: '',
  });

  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setReport({ ...report, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');
    if (token) {
      const decoded = jwtDecode(token);
      const userId = decoded.userId;
      
      const reportWithUserId = { ...report, laborantId: userId };
      
      try {
        await axios.post('http://localhost:8080/report/create', reportWithUserId, {
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
        });
        console.log('Rapor başarıyla oluşturuldu');
        setMessage('Rapor başarıyla oluşturuldu');
        setReport({
          name: '',
          surName: '',
          identifyNumber: '',
          diagnosisTitle: '',
          diagnosisInfo: '',
          reportDate: '',
        });
      } catch (error) {
        console.error('Rapor oluşturulamadı', error);
        setMessage('Rapor oluşturulamadı');
      }
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input type="text" name="name" value={report.name} onChange={handleChange} placeholder="Hasta Adı" required />
        <input type="text" name="surName" value={report.surName} onChange={handleChange} placeholder="Hasta Soyadı" required />
        <input type="text" name="identifyNumber" value={report.identifyNumber} onChange={handleChange} placeholder="TC Kimlik No" required />
        <input type="text" name="diagnosisTitle" value={report.diagnosisTitle} onChange={handleChange} placeholder="Tanı Başlığı" required />
        <textarea name="diagnosisInfo" value={report.diagnosisInfo} onChange={handleChange} placeholder="Tanı Bilgisi" required />
        <input type="date" name="reportDate" value={report.reportDate} onChange={handleChange} placeholder="Rapor Tarihi" required />
        
        <button type="submit">Rapor Oluştur</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
}

export default ReportForm;
