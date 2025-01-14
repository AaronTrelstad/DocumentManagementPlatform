import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { login } from '../../slices/auth';
import axios from 'axios';
import {
  Box,
  TextField,
  Button,
  Typography,
  Alert,
  Collapse,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';

interface UserInfo {
  username: string;
  password: string;
}

function Login() {
  const dispatch = useDispatch();
  const navigate = useNavigate(); 
  const [userInfo, setUserInfo] = useState<UserInfo>({
    username: '',
    password: '',
  });
  const [error, setError] = useState<string>('');
  const [openAlert, setOpenAlert] = useState<boolean>(false); 

  const handleLogin = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/auth/login', userInfo);
      const token = response.data;
      // request user data using token

      if (token) {
        const userData = await axios.get("http://localhost:8080/api/user")
      }
      //dispatch(login({ email, username, role, id }));
      console.log('Login successful');
      navigate('/');
    } catch (error) {
      console.error('Login failed:', error);
      setError('Invalid login credentials'); 
      setOpenAlert(true); 
    }
  };

  const handleRegister = async () => {
    navigate('/register')
  }

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      sx={{ marginTop: 4, maxWidth: 400, marginX: 'auto' }}
    >
      <Typography variant="h4" gutterBottom>
        Login
      </Typography>

      <Collapse in={openAlert}>
        <Alert
          severity="error"
          onClose={() => setOpenAlert(false)}
          sx={{ marginBottom: 2 }}
        >
          {error}
        </Alert>
      </Collapse>

      <TextField
        label="Username or Email"
        variant="outlined"
        fullWidth
        margin="normal"
        name="email"
        value={userInfo.username}
        onChange={(e) =>
          setUserInfo((prev) => ({
            ...prev,
            username: e.target.value,
          }))
        }
      />

      <TextField
        label="Password"
        variant="outlined"
        fullWidth
        margin="normal"
        type="password"
        name="password"
        value={userInfo.password}
        onChange={(e) =>
          setUserInfo((prev) => ({
            ...prev,
            password: e.target.value,
          }))
        }
      />

      <Button
        variant="outlined"
        color="secondary"
        fullWidth
        sx={{ marginTop: 2 }}
        onClick={handleLogin}
      >
        Log In
      </Button>
      <Button
        variant="outlined"
        color="secondary"
        fullWidth
        sx={{ marginTop: 2 }}
        onClick={handleRegister}
      >
        Register
      </Button>
    </Box>
  );
}

export default Login;
