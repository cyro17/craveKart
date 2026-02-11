import { BorderColor, Visibility, VisibilityOff } from '@mui/icons-material';
import { Box, CircularProgress, Divider, IconButton, InputAdornment, TextField, Typography } from '@mui/material'
import React, { useState } from 'react'
import Button from '../UI/Button';
import { useDispatch, useSelector } from 'react-redux';
import { color } from 'motion';
import { loginUser } from '../../State/Authentication/AuthThunks';
import { toast } from 'react-toastify';
import { Navigate, useNavigate } from 'react-router-dom';

export default function LoginPage({onSuccess, switchMode}) {
    const [values, setValues] = useState({ email: "", password: "" });
    const [showPassword, setShowPassword] = useState(false);
    const navigate = useNavigate();
    const [isSubmitting, setSubmitting] = useState(false);
    const { isLoading, error} = useSelector(state => state.auth);

    const dispatch = useDispatch();
    function handleChange(e) {
        setValues({
            ...values,
            [e.target.name]: e.target.value
        })
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            setSubmitting(true);
          const response = await dispatch(loginUser(values));
        
          if (loginUser.fulfilled.match(response)) {
            const { loginData}  = response.payload;
            localStorage.setItem("token", loginData.jwt);
            
              toast.success("Login Successful ! ");
              if (onSuccess) onSuccess();
            setTimeout(() => {
              navigate("/");
            }, 1500);
          }
          else {
            toast.error("Login Failed ! Please check your credentials.");
          } 
        } catch (error) {
          toast.error("An error occurred during login. Please try again.");
          
        } finally {
            setSubmitting(false);
        }
        
      }
    return (
      <>
      <Box component="form" onSubmit={handleSubmit}>
        
          <Typography variant='h6' mb={4} onClick={switchMode} className='hover:cursor-pointer'>
              or <span className='text-rose-600 underline inline-block'>Create an account</span>
          </Typography>
          
          <TextField
              fullWidth
              name='email'
              label="Email Address"
              variant='outlined'
              margin='normal'
              value={values.email}
              onChange={handleChange}
              autoFocus
              sx={{
                  "& .MuiOutlinedInput-root": {
                      backgroundColor: "rgba(255,255,255,0.1)",
                      borderRadius: "12px",
                    },
                }}
                />
          <TextField
              fullWidth
              name='password'
              type={showPassword ? "text" : "password"}
              label="Password"
              margin='normal'
              value={values.password}
              onChange={handleChange}
              sx={{
                  "& .MuiOutlinedInput-root": {
                      backgroundColor: "rgba(255,255,255,0.05)",
                      borderRadius: "12px",
                    },
                }}
                InputProps={{
                    endAdornment: (
                        <InputAdornment position="end">
                    <IconButton
                      onClick={() => setShowPassword(!showPassword)}
                      >
                      {showPassword ? (
                          <VisibilityOff sx={{ color: "gray" }} />
                        ) : (
                            <Visibility sx={{ color: "gray" }} />
                        )}
                    </IconButton>
                  </InputAdornment>
                ),
            }}
            />
           <Typography
                variant="body2"
                textAlign="right"
                sx={{
                    cursor: "pointer",
                    mt: 1,
                    color: "#FF6B40",
                    "&:hover": { textDecoration: "underline" },
                }}
                fontWeight="bold"
                >
                Forgot password?
            </Typography>
        
          <Button size='medium' onClick={handleSubmit}>
              {isLoading ? (
                  <CircularProgress size={22} color='inherit'/>
                ) : (
                    "Login"
                ) }
          </Button>
          <Divider sx={{ my: 4 }}>OR</Divider>
          
          <Button fullWidth variant='outlined'
              sx={{
                  py: 1.3, 
                  borderRadius: "12px", 
                  borderColor: "rgba(255,255,255,0.2)",
                  backgroundColor: "rgba(0, 0, 255, 0.6)",
                  color: "white", 
                  "&:hover": {
                      backgroundColor: "rgba(255,255,255,0.05)",
                    },
                    
                }}
                >
              Continue with Google
          </Button>
        </Box>
    </>
  )
}
