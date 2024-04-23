import React, { useState } from 'react';
import axios from 'axios';
import { TextField, IconButton, Grid, InputAdornment } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';

function ReportSearch({ setReports, showMessage }) {
  const [searchTerm, setSearchTerm] = useState('');

  const handleSearch = async () => {
    try {
      const response = await axios.get('/report/search', {
        params: {
          query: searchTerm,
        },
      });
      setReports(response.data);
    } catch (error) {
      showMessage("Raporlar yüklenirken bir hata oluştu: " + error.message, "error");
    }
  };

  const handleChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleKeyPress = (event) => {
    if (event.key === 'Enter') {
      handleSearch();
    }
  };

  return (
    <div>
      <Grid container spacing={1} alignItems="center">
        <Grid item xs={12}>
          <TextField
            label="Rapor Ara (Ad, Soyad, TC No, Tanı, ...)"
            value={searchTerm}
            onChange={handleChange}
            onKeyPress={handleKeyPress}
            fullWidth
            margin="dense"
            variant="outlined"
            InputProps={{
              endAdornment: (
                <InputAdornment position="end">
                  <IconButton onClick={handleSearch}>
                    <SearchIcon />
                  </IconButton>
                </InputAdornment>
              ),
            }}
          />
        </Grid>
      </Grid>
    </div>
  );
}

export default ReportSearch;
