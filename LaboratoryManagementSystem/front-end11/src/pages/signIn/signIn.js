import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const defaultTheme = createTheme();

export default function SignIn() {
  const navigate = useNavigate();
  const [error, setError] = React.useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const username = data.get('email'); 
    const password = data.get('password');

    try {
      const response = await axios.post('http://localhost:8080/auth/generateToken', {
        username,
        password,
      });

      const token = response.data;
      localStorage.setItem('token', token);
      navigate('/dashboard');
    } catch (error) {
      let errorMessage = 'Giriş Başarısız';
      if (error.response && error.response.status === 401) {
        errorMessage += ': ' + error.response.data.message;
      } else if (error.request) {
        errorMessage = 'Server ile bağlantı kurulamadı';
      } else {
        errorMessage = 'Error: ' + error.message;
      }
      setError(errorMessage);
    }
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign in
          </Typography>
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign In
            </Button>
            {error && <Typography color="error">{error}</Typography>}
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
