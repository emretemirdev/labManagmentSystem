import React, { useState, useEffect } from 'react';
import axios from 'axios';
import TotalReports from './TotalReport'

const TotalReportsContainer = () => {
  const [totalReportsCount, setTotalReportsCount] = useState(null);
  const [currentDate, setCurrentDate] = useState(null);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    axios.get('/report/all')
      .then(response => {
        setTotalReportsCount(response.data.length);
        setCurrentDate(new Date().toLocaleDateString());
      })
      .catch(error => {
        setErrorMessage('Raporlar alınırken bir hata oluştu');
        console.error('Raporlar alınırken bir hata oluştu', error);
      });
  }, []);

  if (totalReportsCount === null || currentDate === null) {
    return <div>Veriler yükleniyor...</div>;
  }

  return (
    <div>
            {errorMessage && <div>{errorMessage}</div>}

      <TotalReports totalReports={totalReportsCount} today={currentDate} />
    </div>
  );
};

export default TotalReportsContainer;
