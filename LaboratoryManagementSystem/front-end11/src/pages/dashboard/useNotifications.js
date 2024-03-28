// useNotifications.js

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
            const token = localStorage.getItem('token');
            const response = await axios.get('/notifications', {
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
            setError('Bildirimler yüklenirken bir hata oluştu.');
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

  return { notifications, loading, error };
};

export default useNotifications;
