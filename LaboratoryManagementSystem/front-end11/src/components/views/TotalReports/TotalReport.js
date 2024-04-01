import React from 'react';
import { Typography, Paper } from '@mui/material';
import Title from '../../../utils/Title'

const TotalReports = ({ totalReports, today }) => {


    return (
      <Paper sx={{ margin: 2, padding: 2 }}>
        <Title>Toplam Rapor</Title>
        <Typography component="p" variant="h4">
          {totalReports !== null ? totalReports : "Veri yüklenemedi"}
        </Typography>
        <Typography color="text.secondary" sx={{ flex: 1 }}>
          Bugünün Tarihi: {today !== null ? today : "Tarih yüklenemedi"}
        </Typography>
      </Paper>
    );
  };
  
  export default TotalReports;