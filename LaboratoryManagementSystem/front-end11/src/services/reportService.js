import axios from 'axios';


export const fetchReports = async () => {
  try {
    const response = await axios.get('/report/all');
    return response.data;
  } catch (error) {
    console.error('Error fetching reports:', error);
    throw error;
  }
};

export const getReportCountsByDate = (reports) => {
  const countsByDate = reports.reduce((acc, { reportDate }) => {
    acc[reportDate] = (acc[reportDate] || 0) + 1;
    return acc;
  }, {});

  return Object.entries(countsByDate).map(([date, count]) => ({ date, count }));
};


export const updateReport = async (reportId, reportData, token) => {
  try {
    const response = await axios.put(`/report/${reportId}`, reportData, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error('Rapor güncellenirken hata oluştu:', error);
    throw error;
  }
};


export const fetchDataForChart = async () => {
    const reports = await fetchReports();
    return getReportCountsByDate(reports);
  };