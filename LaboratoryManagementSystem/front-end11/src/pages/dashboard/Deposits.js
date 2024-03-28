import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Typography, Paper } from '@mui/material';
import Title from '../../Title';

export default function Deposits() {
  const [totalReports, setTotalReports] = useState(0); // Toplam rapor sayısını tutacak state
  const [today, setToday] = useState(''); // Bugünün tarihini tutacak state

  useEffect(() => {
    fetchReportData(); // Component yüklendiğinde rapor verilerini almak için useEffect kullanıyoruz
  }, []);

  const fetchReportData = async () => {
    try {
      const response = await axios.get('/report/all'); // Backend'den tüm raporları al
      const reports = response.data; // Gelen rapor verilerini depoluyoruz
      const totalReports = reports.length; // Toplam rapor sayısını al
      const today = new Date().toLocaleDateString(); // Bugünün tarihini al
      setTotalReports(totalReports); // Toplam rapor sayısını state'e ayarla
      setToday(today); // Bugünün tarihini state'e ayarla
    } catch (error) {
      console.error('Error fetching report data:', error); // Hata durumunda konsola hata mesajını yazdır
    }
  };

  return (
    <Paper sx={{ margin: 2, padding: 2 }}>
      <Title>Toplam Raporlar</Title>
      <Typography component="p" variant="h4">
        {totalReports}
      </Typography>
      <Typography color="text.secondary" sx={{ flex: 1 }}>
        Bugünün Tarihi: {today}
      </Typography>
    </Paper>
  );
}
