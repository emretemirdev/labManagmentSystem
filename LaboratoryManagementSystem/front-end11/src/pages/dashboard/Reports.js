import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Table, TableBody, TableCell, TableHead, TableRow, Paper } from '@mui/material';
import Title from '../../utils/Title';

export default function Reports() {
  const [reports, setReports] = useState([]);
  const [selectedReport, setSelectedReport] = useState({});
  const [openDetailsDialog, setOpenDetailsDialog] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchReports();
  }, []);

  const fetchReports = async () => {
    try {
      const response = await axios.get('/report/all');
      setReports(response.data);
    } catch (error) {
      console.error("Raporlar yüklenirken bir hata oluştu", error);
    }
  };

  const handleDetailsOpen = (report) => {
    setSelectedReport(report);
    setOpenDetailsDialog(true);
  };

  const handleDetailsClose = () => {
    setOpenDetailsDialog(false);
  };

  const [deleteError, setDeleteError] = useState(null);

  const handleDelete = async (reportId) => {
    try {
      const token = localStorage.getItem('token');
      const config = {
        headers: {
          Authorization: `Bearer ${token}`
        }
      };
      await axios.delete(`/report/delete/${reportId}`, config);
      fetchReports();
    } catch (error) {
      let errorMessage = 'Rapor silinirken bir hata oluştu';
      if (error.response) {
        if (error.response.status === 403) {
          errorMessage = 'Bu işlemi gerçekleştirmek için yetkiniz bulunmamaktadır.';
          setDeleteError(errorMessage); 
        } else if (error.response.status === 404) {
          errorMessage = 'Silinmek istenen rapor bulunamadı.';
        }
      }
      console.error(errorMessage, error);
    }
  };
  

  const [deleteConfirmation, setDeleteConfirmation] = useState(null);

  const handleDeleteConfirmation = (reportId) => {
    const reportToDelete = reports.find(report => report.id === reportId);
    const confirmationMessage = `ID: ${reportToDelete.id}'si olan "${reportToDelete.name}" adlı raporu silmek istediğinize emin misiniz?`;
  
    setDeleteConfirmation({
      open: true,
      reportId: reportId,
      message: confirmationMessage
    });
  };
  
  
  const handleDeleteConfirmed = async () => {
    await handleDelete(deleteConfirmation.reportId);
    setDeleteConfirmation(null);
  };
  const handleDeleteCanceled = () => {
    setDeleteConfirmation(null);
    console.log('Silme işlemi iptal edildi.');
  };
  return (
    <Paper sx={{ margin: 2, padding: 2 }}>
      <Title>Raporlar</Title>
      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>Tarih</TableCell>
            <TableCell>Ad</TableCell>
            <TableCell>Tanı Başlığı</TableCell>
            <TableCell align="right">İşlemler</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {reports.map((report) => (
            <TableRow key={report.id}>
              <TableCell>{report.reportDate}</TableCell>
              <TableCell>{report.name}</TableCell>
              <TableCell>{report.diagnosisTitle}</TableCell>
              <TableCell align="right">
                <Button onClick={() => handleDetailsOpen(report)}>Detaylar</Button>
                <Button onClick={() => handleDeleteConfirmation(report.id)}>Sil</Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
  
      <Dialog open={openDetailsDialog} onClose={handleDetailsClose}>
        <DialogTitle>Rapor Detayları</DialogTitle>
        <DialogContent>
          <DialogContentText>Tarih: {selectedReport.reportDate}</DialogContentText>
          <DialogContentText>Id: {selectedReport.id}</DialogContentText>
          <DialogContentText>Ad: {selectedReport.name}</DialogContentText>
          <DialogContentText>Soyad: {selectedReport.surName}</DialogContentText>
          <DialogContentText>TC Kimlik No: {selectedReport.identifyNumber}</DialogContentText>
          <DialogContentText>Tanı Başlığı: {selectedReport.diagnosisTitle}</DialogContentText>
          <DialogContentText>Tanı Bilgisi: {selectedReport.diagnosisInfo}</DialogContentText>
          <DialogContentText>Laborant Adı: {selectedReport.laborantName}</DialogContentText>
          <DialogContentText>Laborant Kullanıcı Adı: {selectedReport.laborantUsername}</DialogContentText>
          {selectedReport.reportPic && (
      <img src={selectedReport.reportPic} alt="Rapor Fotoğrafı" style={{ width: '100%', marginTop: '20px' }} />
    )}
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDetailsClose}>Kapat</Button>
        </DialogActions>
      </Dialog>
  
      <Dialog open={deleteConfirmation && deleteConfirmation.open} onClose={handleDeleteCanceled}>
  <DialogTitle>Emin misiniz?</DialogTitle>
  <DialogContent>
    <DialogContentText>{deleteConfirmation && deleteConfirmation.message}</DialogContentText>
  </DialogContent>
  <DialogActions>
    <Button onClick={handleDeleteCanceled}>İptal</Button>
    <Button onClick={handleDeleteConfirmed}>Sil</Button>
  </DialogActions>
</Dialog>

    </Paper>
  );
}
