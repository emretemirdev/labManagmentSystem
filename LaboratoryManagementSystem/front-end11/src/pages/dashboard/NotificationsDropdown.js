import React from 'react';
import useNotifications from './useNotifications';
import { List, ListItem, ListItemAvatar, ListItemText, Popover, Avatar,Button, Typography,Box } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import NotificationImportantIcon from '@mui/icons-material/NotificationImportant';
import IconButton from '@mui/material/IconButton';
import Badge from '@mui/material/Badge';
import NotificationsIcon from '@mui/icons-material/Notifications';
import AddIcon from '@mui/icons-material/Add';
import UpdateIcon from '@mui/icons-material/Update';

const timeSince = (date) => {
  const seconds = Math.floor((new Date() - new Date(date)) / 1000);

  let interval = seconds / 31536000;

  if (interval > 1) {
    return Math.floor(interval) + " yıl önce";
  }
  interval = seconds / 2592000;
  if (interval > 1) {
    return Math.floor(interval) + " ay önce";
  }
  interval = seconds / 86400;
  if (interval > 1) {
    return Math.floor(interval) + " gün önce";
  }
  interval = seconds / 3600;
  if (interval > 1) {
    return Math.floor(interval) + " saat önce";
  }
  interval = seconds / 60;
  if (interval > 1) {
    return Math.floor(interval) + " dakika önce";
  }
  return Math.floor(seconds) + " saniye önce";
};

const NotificationsDropdown = ({ isAdmin }) => {
  const { notifications, loading, error,deleteAllNotifications  } = useNotifications();
  const [anchorEl, setAnchorEl] = React.useState(null);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  
  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleDeleteAll = () => {
    deleteAllNotifications(); 
    handleClose(); 
  };
  const open = Boolean(anchorEl);
  const id = open ? 'simple-popover' : undefined;

  const getIcon = (notificationType) => {
    switch (notificationType) {
      case 'SILME':
        return <DeleteIcon />;
      case 'OLUSTURMA':
        return <AddIcon />;
      case 'GUNCELLEME':
        return <UpdateIcon />;
      default:
        return <NotificationImportantIcon />;
    }
  };

  const sortedNotifications = notifications.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));


  return (
    <div>
      <IconButton color="inherit" onClick={handleClick}>
        <Badge badgeContent={sortedNotifications.length} color="secondary">
          <NotificationsIcon />
        </Badge>
      </IconButton>
      <Popover
        id={id}
        open={open}
        anchorEl={anchorEl}
        onClose={handleClose}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
        transformOrigin={{ vertical: 'top', horizontal: 'center' }}
        PaperProps={{
          style: {
            maxHeight: '400px',
            width: '300px',
            overflow: 'auto',
          },
        }}
      >
        <Box textAlign="center" p={2}>
          <Button variant="contained" color="secondary" onClick={handleDeleteAll}>
            Tüm Bildirimleri Sil
          </Button>
          {error && <Typography color="error">{error}</Typography>}
        </Box>
        <List>
          {loading ? <ListItem><ListItemText primary="Bildirimler Yükleniyor..." /></ListItem> : sortedNotifications.map((notification) => (
            <ListItem key={notification.id} divider>
              <ListItemAvatar>
                <Avatar>{getIcon(notification.notificationType)}</Avatar>
              </ListItemAvatar>
              <ListItemText
                primary={`${notification.laborantName} tarafından ${notification.reportName} adlı rapor`}
                secondary={`${notification.message} - ${timeSince(notification.createdAt)}`}
              />
            </ListItem>
          ))}
        </List>
      </Popover>
    </div>
  );
};


export default NotificationsDropdown;
