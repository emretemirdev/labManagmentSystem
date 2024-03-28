import React from 'react';
import useNotifications from './useNotifications';
import { List, ListItem, ListItemAvatar, ListItemText, Popover, Avatar } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import NotificationImportantIcon from '@mui/icons-material/NotificationImportant';
import IconButton from '@mui/material/IconButton';
import Badge from '@mui/material/Badge';
import NotificationsIcon from '@mui/icons-material/Notifications';
import AddIcon from '@mui/icons-material/Add';
import UpdateIcon from '@mui/icons-material/Update';

// Ne kadar süre önce olduğunu hesaplayan fonksiyon
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
  const { notifications, loading, error } = useNotifications();
  const [anchorEl, setAnchorEl] = React.useState(null);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  
  const handleClose = () => {
    setAnchorEl(null);
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

  // Bildirimleri en yeniden en eskiye doğru sıralayın
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
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'center',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'center',
        }}
        style={{
          marginTop: '10px',
        }}
      >
        <List style={{
          width: '300px',
          maxWidth: '300px',
          backgroundColor: 'white',
          maxHeight: '400px',
          overflow: 'auto',
        }}>
          {loading && <ListItem><ListItemText primary="Bildirimler Yükleniyor..." /></ListItem>}
          {error && <ListItem><ListItemText primary={error} /></ListItem>}
          {!loading && !error && sortedNotifications.map((notification) => (
            <ListItem key={notification.id} alignItems="flex-start" style={{
              borderBottom: '1px solid #f0f0f0',
            }}>
              <ListItemAvatar>
                <Avatar>
                  {getIcon(notification.notificationType)}
                </Avatar>
              </ListItemAvatar>
              <ListItemText
                primary={`${notification.laborantName} tarafından adı ${notification.reportName} ve ıd'si ${notification.reportId} olan rapor ${notification.message}`}
                secondary={timeSince(notification.createdAt)}
                
              />
            </ListItem>
          ))}
        </List>
      </Popover>
    </div>
  );
};

export default NotificationsDropdown;
