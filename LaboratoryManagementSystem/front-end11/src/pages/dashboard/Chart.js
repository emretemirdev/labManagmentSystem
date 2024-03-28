import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Typography, Paper } from '@mui/material';
import Title from '../../Title';
import { LineChart } from '@mui/x-charts';
import { axisClasses } from '@mui/x-charts';

export default function ReportsChart() {
  const [reportData, setReportData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchReportData();
  }, []);

  const fetchReportData = async () => {
    try {
      const response = await axios.get('/report/all');
      const reports = response.data;
      const sortedReports = sortReportsByDate(reports);
      setReportData(sortedReports);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching report data:', error);
    }
  };

  const sortReportsByDate = (reports) => {
    return reports.sort((a, b) => new Date(a.reportDate) - new Date(b.reportDate));
  };

  const generateChartData = () => {
    const today = new Date().toLocaleDateString(); // Bugünün tarihini al
    const todayIndex = reportData.findIndex(report => report.reportDate === today); // Bugünün indeksini bul
    const beforeToday = reportData.slice(0, todayIndex); // Bugünden önceki raporları al
    const afterToday = reportData.slice(todayIndex + 1); // Bugünden sonraki raporları al

    const beforeTodayData = beforeToday.map(report => {
      const reportDate = new Date(report.reportDate);
      return { date: reportDate.toLocaleDateString(), count: 1 }; // Her rapor için 1 sayısı
    });

    const afterTodayData = afterToday.map(report => {
      const reportDate = new Date(report.reportDate);
      return { date: reportDate.toLocaleDateString(), count: 1 }; // Her rapor için 1 sayısı
    });

    // Bugünden önceki ve sonraki günlerdeki raporları birleştir
    const chartData = [...beforeTodayData, { date: today, count: 1 }, ...afterTodayData];
    
    console.log('Chart Data:', chartData); // Oluşturulan grafik verilerini konsola yazdır
    return chartData;
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <Paper sx={{ margin: 2, padding: 2 }}>
      <Title>Rapor Grafiği</Title>
      <Typography component="div" sx={{ width: '100%', flexGrow: 1, overflow: 'hidden' }}>
        <LineChart
          dataset={generateChartData()}
          margin={{
            top: 16,
            right: 20,
            left: 70,
            bottom: 30,
          }}
          xAxis={[
            {
              scaleType: 'point',
              dataKey: 'date',
              tickNumber: 2,
              tickLabelStyle: { fontSize: 12 },
            },
          ]}
          yAxis={[
            {
              label: 'Rapor Sayısı',
              labelStyle: { fontSize: 12 },
              tickLabelStyle: { fontSize: 12 },
              tickNumber: 3,
            },
          ]}
          series={[
            {
              dataKey: 'count',
              showMark: false,
            },
          ]}
          sx={{
            [`.${axisClasses.root} line`]: { stroke: 'rgba(0, 0, 0, 0.5)' },
            [`.${axisClasses.root} text`]: { fill: 'rgba(0, 0, 0, 0.7)' },
          }}
        />
      </Typography>
    </Paper>
  );
}
