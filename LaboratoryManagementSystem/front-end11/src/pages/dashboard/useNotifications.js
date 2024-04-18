import { useState, useEffect } from 'react';
import axios from 'axios';

const useNotifications = () => {
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const fetchNotifications = async () => {
      try {
        const response = await axios.get('/notification', {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setNotifications(response.data.map(notification => ({
          id: notification.id,
          createdAt: notification.createdAt,
          laborantName: notification.laborantName,
          message: notification.message,
          notificationType: notification.notificationType,
          reportId: notification.reportId,
          reportName: notification.reportName
        })));

      } catch (err) {
        if (err.response && err.response.status === 403) {
          setError('Sadece Yöneticiniz bildirimleri görüntüleyebilir.');
        } else {
          setError(`Bildirimler yüklenirken bir hata oluştu: ${err.message}`);
        }
      } finally {
        setLoading(false);
      }
    };

    if (token) {
      fetchNotifications();
    } else {
      setError('Kimlik doğrulama bilgisi bulunamadı.');
      setLoading(false);
    }
  }, []);

  const deleteAllNotifications = async () => {
    const token = localStorage.getItem('token');
    try {
      await axios.delete('/notification/deleteAll', {
        headers: { Authorization: `Bearer ${token}` }
      });
      setNotifications([]); 
    } catch (err) {
      setError('Bildirimleri silerken bir hata oluştu.');
    }
  };

  return { notifications, loading, error, deleteAllNotifications };
};

export default useNotifications;
