import React, { useState, useEffect } from 'react';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import { MainListItems } from '../../utils/listItems';
import Reports from '../../components/views/Reports';
import NotificationsDropdown from '../../components/containers/NotificationsDropdown';
import { useNavigate } from "react-router-dom";
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import { getUserInfo } from '../../services/userService'; // Kullanıcı servisi import ediliyor
import AppBar from '../../components/ui/AppBar';
import Drawer from '../../components/ui/Drawer';
import CreateReportForm from '../../components/views/CreateReport/CreateReports';
import TotalReportsContainer from '../../components/containers/TotalReportContainer';
import WelcomeBanner from '../../components/containers/WelcomeBanner';


const defaultTheme = createTheme();

export default function Dashboard() {
  const [open, setOpen] = useState(true);
  const [userInfo, setUserInfo] = useState({});
  const [showCreateReport, setShowCreateReport] = useState(false);

  const navigate = useNavigate();


  const toggleDrawer = () => {
    setOpen(!open);
  };

  const handleCreateReportClick = () => {
    setShowCreateReport(true);
  };

  const handleReportCreated = () => {
    setShowCreateReport(false);
  };


  
  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const userData = await getUserInfo();
        setUserInfo(userData);
      } catch (error) {
        console.error('Kullanıcı bilgileri alınamadı', error);
      }
    };
    fetchUserInfo();
  }, []);




  return (
    <ThemeProvider theme={defaultTheme}>
      <Box sx={{ display: 'flex' }}>
        <CssBaseline />
        <AppBar position="absolute" open={open}>
          <Toolbar
            sx={{
              pr: '24px', // keep right padding when drawer closed
            }}
          >
            <IconButton
              edge="start"
              color="inherit"
              aria-label="open drawer"
              onClick={toggleDrawer}
              sx={{
                marginRight: '36px',
                ...(open && { display: 'none' }),
              }}
            >
              <MenuIcon />
            </IconButton>
            <Typography
              component="h1"
              variant="h6"
              color="inherit"
              noWrap
              sx={{ flexGrow: 1 }}
            >
              Laboratuvar Bilgi Yönetim Sistemi
            </Typography>
            <NotificationsDropdown />
            <IconButton color="inherit" onClick={handleLogout}>
              <ExitToAppIcon />
            </IconButton>
          </Toolbar>
        </AppBar>
        <Drawer variant="permanent" open={open}>
          <Toolbar
            sx={{
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'flex-end',
              px: [1],
            }}
          >
            <IconButton onClick={toggleDrawer}>
              <ChevronLeftIcon />
            </IconButton>
          </Toolbar>
          <Divider />
          <List>
            <MainListItems onCreateReportClick={handleCreateReportClick} onShowReports={handleReportCreated} />
          </List>
        </Drawer>
        <Box
          component="main"
          sx={{
            backgroundColor: (theme) =>
              theme.palette.mode === 'light'
                ? theme.palette.grey[100]
                : theme.palette.grey[900],
            flexGrow: 1,
            height: '100vh',
            overflow: 'auto',
          }}
        >
          <Toolbar />

          <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
            <Grid container spacing={3}>
              <Grid item xs={12} md={8} lg={9}>
                <Paper
                  sx={{
                    p: 2,
                    display: 'flex',
                    flexDirection: 'column',
                    height: 240,
                  }}
                >
                  <WelcomeBanner
                    userName={userInfo.name}
                    hospitalId={userInfo.hospitalId}
                    roles={userInfo.roles}
                  />
                </Paper>

              </Grid>
              <Grid item xs={12} md={4} lg={3}>
                <Paper
                  sx={{
                    p: 2,
                    display: 'flex',
                    flexDirection: 'column',
                    height: 240,
                  }}
                >
                  <TotalReportsContainer />
                </Paper>
              </Grid>
              <Grid item xs={12}>
                <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
                  {showCreateReport ? (
                    <CreateReportForm onReportCreated={handleReportCreated} />
                  ) : (
                    <Reports />
                  )}
                </Paper>
              </Grid>
            </Grid>
          </Container>


        </Box>
      </Box>
    </ThemeProvider>
  );
}