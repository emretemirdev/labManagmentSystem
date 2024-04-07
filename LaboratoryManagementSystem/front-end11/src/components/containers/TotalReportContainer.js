import React, { useState, useEffect } from 'react';
import axios from 'axios';
import TotalReports from '../views/TotalReports/TotalReport'

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
       setErrorMessage('An error occurred while fetching total reports.');
      });
  }, []);

  if (totalReportsCount === null || currentDate === null) {
    return <div>Veriler yükleniyor...</div>;
  }

  return <TotalReports totalReports={totalReportsCount} today={currentDate} />;
};

export default TotalReportsContainer;
