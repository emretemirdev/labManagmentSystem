import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Button, Snackbar, Dialog, DialogActions, TextField, DialogContent, DialogContentText, DialogTitle, Table, TableBody, TableCell, TableHead, TableRow, Paper } from '@mui/material';
import Title from '../../utils/Title';
import { updateReport } from '../../services/reportService';

export default function Reports() {
  const [reports, setReports] = useState([]);
  const [selectedReport, setSelectedReport] = useState({});
  const [openDetailsDialog, setOpenDetailsDialog] = useState(false);
  const [openEditDialog, setOpenEditDialog] = useState(false);
  const [message, setMessage] = useState({ text: '', type: '' }); // Mesajlar için tek state
  const [deleteConfirmation, setDeleteConfirmation] = useState(null);


  useEffect(() => {
    fetchReports();
  }, []);

  const showMessage = (text, type = 'error') => {
    setMessage({ text, type });
  };

  const handleCloseMessage = () => {
    setMessage({ text: '', type: '' });
  };

  const fetchReports = async () => {
    try {
      const response = await axios.get('/report/all');
      setReports(response.data);
    } catch (error) {
      showMessage("Raporlar yüklenirken bir hata oluştu: " + error.message, "error");
    }
  };


  const handleDetailsOpen = (report) => {
    setSelectedReport(report);
    setOpenDetailsDialog(true);
  };

  const handleDetailsClose = () => {
    setOpenDetailsDialog(false);
  };


  const handleDelete = async (reportId) => {
    try {
      const token = localStorage.getItem('token');
      const config = {
        headers: {
          Authorization: `Bearer ${token}`
        }
      };
      const response = await axios.delete(`/report/delete/${reportId}`, config);

      fetchReports(); // refresh the list after deletion
    } catch (error) {
      console.error('Delete error:', error);
      let errorMessage = 'Rapor silinirken bir hata oluştu';
      if (error.response) {
        if (error.response.status === 403) {
          errorMessage = 'Bu işlemi gerçekleştirmek için yetkiniz bulunmamaktadır. Yalnızca Admin Rapor Silme İşlemi Yapabilir.';
        } else if (error.response.status === 404) {
          errorMessage = 'Silinmek istenen rapor bulunamadı.';
        }
      }
      showMessage(errorMessage, "error");
    }
  };



  const handleDeleteConfirmation = (reportId) => {
    const reportToDelete = reports.find(report => report.id === reportId);
    if (!reportToDelete) {
      console.error('Rapor bulunamadı');
      return;
    }
    const confirmationMessage = `ID: ${reportToDelete.id}'si olan "${reportToDelete.name}" adlı raporu silmek istediğinize emin misiniz?`;
    setDeleteConfirmation({
      open: true,
      reportId: reportId,
      message: confirmationMessage
    });
  };



  const handleDeleteConfirmed = async () => {
    if (deleteConfirmation && deleteConfirmation.open) {
      await handleDelete(deleteConfirmation.reportId);
      setDeleteConfirmation(null); // State'i sıfırla

    } else {
      showMessage('Silme işlemi onaylanmadı', 'error');
    }
  };



  const handleDeleteCanceled = () => {
    if (deleteConfirmation && deleteConfirmation.open) {
      showMessage('Silme işlemi iptal edildi.');
      setDeleteConfirmation(null); // State'i sıfırla
    }
  };

  const handleEditOpen = (report) => {
    setSelectedReport(report);
    setOpenEditDialog(true);
  };

  const handleUpdate = async (report) => {
    try {
      const token = localStorage.getItem('token');
      if (typeof report.id === 'undefined') {
        throw new Error('Rapor ID tanımsız!');
      }
      const updatedReportData = { ...report };
      await updateReport(report.id, updatedReportData, token);
      showMessage('Rapor başarıyla güncellendi', 'success');
      fetchReports();
      setOpenEditDialog(false); // Formu kapat
    } catch (error) {
      showMessage('Rapor güncellenirken hata oluştu: ' + error.message, 'error');
    }
  };




  return (
    <Paper sx={{ margin: 2, padding: 2 }}>
      <Title>Raporlar</Title>
      {message.text && (
        <Snackbar
          anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
          open={Boolean(message.text)}
          autoHideDuration={6000}
          onClose={handleCloseMessage}
          message={message.text}
        />
      )}
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
                <Button onClick={() => handleEditOpen(report)}>Güncelle</Button>
                <Button onClick={() => handleDeleteConfirmation(report.id)}>Sil</Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Dialog open={openEditDialog} onClose={() => setOpenEditDialog(false)}>
        <DialogTitle>Raporu Güncelle</DialogTitle>
        <DialogContent>
          <form onSubmit={(e) => {
            e.preventDefault();
            handleUpdate(selectedReport);
          }}>
            <TextField
              label="Ad"
              value={selectedReport.name}
              onChange={(e) => setSelectedReport({ ...selectedReport, name: e.target.value })}
              fullWidth
            />
            <TextField
              label="Tanı Başlığı"
              value={selectedReport.diagnosisTitle}
              onChange={(e) => setSelectedReport({ ...selectedReport, diagnosisTitle: e.target.value })}
              fullWidth
            />
            {/* Diğer rapor alanları için benzer giriş alanlarını ekleyebilirsiniz */}
            <Button type="submit">Güncelle</Button>
          </form>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenEditDialog(false)}>İptal</Button>
        </DialogActions>
      </Dialog>


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

      <Dialog open={!!deleteConfirmation?.open} onClose={handleDeleteCanceled}>
        <DialogTitle>Emin misiniz?</DialogTitle>
        <DialogContent>
          <DialogContentText>{deleteConfirmation?.message}</DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDeleteCanceled}>İptal</Button>
          <Button onClick={handleDeleteConfirmed}>Sil</Button>
        </DialogActions>
      </Dialog>

    </Paper>
  );
}
