import React, { useState } from 'react';
import axios from 'axios';
import jwtDecode from 'jwt-decode';
import { Container, TextField, Button, Grid, Paper, Typography, InputLabel, Input } from '@mui/material';
axios.defaults.baseURL = 'http://localhost:8080';

export default function ReportForm() {
  const [report, setReport] = useState({
    name: '',
    surName: '',
    identifyNumber: '',
    diagnosisTitle: '',
    diagnosisInfo: '',
    reportDate: '',
  });

  const [reportPic, setReportPic] = useState(null); 
  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setReport({ ...report, [name]: value });
};
const handleFileChange = (e) => {
  setReportPic(e.target.files[0]); 
};
  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');
    if (token) {
      const reportData = { ...report, laborantId: jwtDecode(token).userId }; // jwtDecode ile token'dan kullanıcı ID'sini alıyoruz.
      const formData = new FormData();
      formData.append('report', JSON.stringify(reportData));
      if (reportPic) {
        formData.append('reportPic', reportPic);
      }

      try {
        const response = await axios.post('/report/create', formData, {
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });

        setReport({
          name: '',
          surName: '',
          identifyNumber: '',
          diagnosisTitle: '',
          diagnosisInfo: '',
          reportDate: '',
        });
        setReportPic(null);
        setMessage('Rapor başarıyla oluşturuldu.');
      } catch (error) {
        setMessage('Rapor oluşturulamadı.');
      }
    } else {
      setMessage('Oturum bilgisi bulunamadı.');
    }
  };
  
  return (
    <Container component={Paper} elevation={3} sx={{ p: 4 }}>
      <Typography variant="h6" gutterBottom>
        Rapor Oluştur
      </Typography>
      <form onSubmit={handleSubmit}>
        <Grid container spacing={2}>
          <Grid item xs={12} sm={6}>
            <TextField
              required
              id="name"
              name="name"
              label="Hasta Adı"
              fullWidth
              variant="outlined"
              value={report.name}
              onChange={handleChange}
              inputProps={{ maxLength: 25 }} // Maksimum 25 karakter
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              required
              id="surName"
              name="surName"
              label="Hasta Soyadı"
              fullWidth
              variant="outlined"
              value={report.surName}
              onChange={handleChange}
              inputProps={{ maxLength: 25 }} // Maksimum 25 karakter
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              required
              id="identifyNumber"
              name="identifyNumber"
              label="TC Kimlik No"
              fullWidth
              variant="outlined"
              value={report.identifyNumber}
              onChange={handleChange}
              inputProps={{ minLength: 11, maxLength: 11 }} // 11 karakter
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              required
              id="diagnosisTitle"
              name="diagnosisTitle"
              label="Tanı Başlığı"
              fullWidth
              variant="outlined"
              value={report.diagnosisTitle}
              onChange={handleChange}
              inputProps={{ maxLength: 70 }} // Maksimum 70 karakter
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              required
              id="diagnosisInfo"
              name="diagnosisInfo"
              label="Tanı Bilgisi"
              fullWidth
              multiline
              rows={4}
              variant="outlined"
              value={report.diagnosisInfo}
              onChange={handleChange}
              inputProps={{ maxLength: 700 }} // Maksimum 200 karakter
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              required
              id="reportDate"
              name="reportDate"
              label="Rapor Tarihi"
              type="date"
              fullWidth
              InputLabelProps={{
                shrink: true,
              }}
              variant="outlined"
              value={report.reportDate}
              onChange={handleChange}
            />
          </Grid>
          <Grid item xs={12}>
            <InputLabel htmlFor="reportPic">Rapor Fotoğrafı</InputLabel>
            <Input
              id="reportPic"
              name="reportPic"
              type="file"
              onChange={handleFileChange}
              fullWidth
            />
          </Grid>
          <Grid item xs={12}>
            <Button type="submit" variant="contained" color="primary">
              Rapor Oluştur
            </Button>
          </Grid>
          <Grid item xs={12} style={{ marginTop: 20 }}>
            <Typography color={message.includes('başarıyla') ? 'primary' : 'error'}>
              {message}
            </Typography>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}
