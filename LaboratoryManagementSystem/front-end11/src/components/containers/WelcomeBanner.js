import React from 'react';
import { Paper, Typography, Box, useTheme } from '@mui/material';
import WelcomeBannerImage from '../../assets/welcome-banner.png'; // Make sure the path is correct

export default function WelcomeBanner({ userName, hospitalId, roles }) {
  const theme = useTheme();

  const formatRoles = (roles) => {
    if (Array.isArray(roles)) {
      return roles.map((role) => {
        if (role === 'ROLE_USER') return 'LABORANT';
        // Sunucudan başka roller de dönüyor olabilir. Gerekiyorsa buraya ekleyin.
        return role.replace('ROLE_', '');
      }).join(', ');
    }
    return '';
  };

  return (
    <Paper
      sx={{
        position: 'relative',
        backgroundImage: `url(${WelcomeBannerImage})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
        width: '100%',
        height: '190%', // Adjust the height as needed
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        padding: theme.spacing(2),
        color: theme.palette.common.white,
        borderRadius: theme.shape.borderRadius,
        overflow: 'hidden',
        '&:before': {
          content: '""',
          position: 'absolute',
          top: 0,
          right: 0,
          bottom: 0,
          left: 0,
          backgroundColor: 'rgba(0, 0, 0, 0.5)',
        },
      }}
    >
      <Box
        sx={{
          position: 'absolute',
          boxShadow: '0px 0px 2px 1px rgba(0,0,0,0.2)', // Gölgenin rengi ve boyutu
          padding: theme.spacing(1),
          borderRadius: theme.shape.borderRadius,
          top: theme.spacing(3),
          right: theme.spacing(3),
        }}
      >
        <Typography variant="h4" sx={{ fontWeight: 'bold', zIndex: 1 }}>
          İyi Günler, {userName}!
        </Typography>
      </Box>


      <Box
        sx={{
          position: 'absolute',
          bottom: theme.spacing(8),
          right: theme.spacing(2),
          boxShadow: '0px 0px 2px 1px rgba(0,0,0,0.2)', // Gölgenin rengi ve boyutu
          padding: theme.spacing(1),
          borderRadius: theme.shape.borderRadius,

        }}
      >

        <Typography variant="subtitle1" sx={{ zIndex: 1 }}>
          Hastane Kimlik Numaranız: {hospitalId}
        </Typography>
      </Box>



      <Box
        sx={{
          position: 'absolute',
          bottom: theme.spacing(1),
          right: theme.spacing(2),
          boxShadow: '0px 0px 2px 1px rgba(0,0,0,0.2)', // Gölgenin rengi ve boyutu
          padding: theme.spacing(1),
          borderRadius: theme.shape.borderRadius,

        }}
      >

        <Typography variant="subtitle1" sx={{ zIndex: 1 }}>
          Rolünüz: {formatRoles(roles)}
        </Typography>
      </Box>



    </Paper>
  );
}
