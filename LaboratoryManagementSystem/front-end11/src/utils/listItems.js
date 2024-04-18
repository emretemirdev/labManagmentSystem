import React from 'react';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import HomeIcon from '@mui/icons-material/Home';
import AddIcon from '@mui/icons-material/Add';
import LogoutIcon from '@mui/icons-material/Logout';
import { useNavigate } from 'react-router-dom';

export const MainListItems = ({ onCreateReportClick, onShowReports }) => {
  const navigate = useNavigate();

  return (
    <React.Fragment>

      <ListItemButton onClick={onShowReports}>
        <ListItemIcon>
          <HomeIcon />
        </ListItemIcon>
        <ListItemText primary="Ana Sayfa" />
      </ListItemButton>

      <ListItemButton onClick={onCreateReportClick}>
        <ListItemIcon>
          <AddIcon />
        </ListItemIcon>
        <ListItemText primary="Rapor Oluştur" />
      </ListItemButton>

      <ListItemButton onClick={() => {
        localStorage.removeItem('token');
        navigate('/login');
      }}>
        <ListItemIcon>
          <LogoutIcon />
        </ListItemIcon>
        <ListItemText primary="Çıkış Yap" />
      </ListItemButton>

    </React.Fragment>
  );
};
