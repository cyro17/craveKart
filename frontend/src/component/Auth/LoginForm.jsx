import React from 'react';
import { Backdrop, Button, CircularProgress, TextField, Typography } from '@mui/material'
import {  useNavigate } from 'react-router-dom'
import { Field, Formik, Form } from 'formik'
import { useDispatch, useSelector } from 'react-redux';
import { toast } from 'react-toastify';
import { loginUser } from '../../State/Authentication/AuthThunks';

const initialValues = {
  email: "", 
  password: "",
}
// const MotionButton = motion.create(Button);

export default function LoginForm() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { isLoading } = useSelector(state => state.auth);
  
  const handleSubmit = async (values, { setSubmitting }) => {

    try {
      const response = await dispatch(loginUser(values));
    
      if (loginUser.fulfilled.match(response)) {
        const { loginData}  = response.payload;
        localStorage.setItem("token", loginData.jwt);
        
        toast.success("Login Successful ! ");
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
    <div>
      <Typography variant='h5' className='text-center'>
        Login 
      </Typography>
        <Formik initialValues={initialValues} onSubmit={handleSubmit}>
        {({ isSubmitting}) => (
          <Form>
          <Field
            as={TextField}
            name="email"
            label="email"
            fullWidth
            variant="outlined"
            margin="normal"
          />
          <Field
            as={TextField}
            name="password"
            label="password"
            fullWidth
            variant="outlined"
            margin="normal"
          />
        <Button sx={{ mt: 2, padding: "1rem " }}
          onHoverStart={{
            scale: 1.2
          }}
          fullWidth
          type='submit'
          variant='contained'
          disabled={isSubmitting}  
        >
            {isSubmitting ? "Logging in..." : "Login"}
          </Button>

            <Backdrop
              open={isSubmitting || isLoading}
              sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
            >
              <CircularProgress color="inherit" />
            </Backdrop>
          
        </Form>
        )}
        </Formik>
      <Typography variant='body2' align='center' sx={{mt:3}}>
        Don't have account ?
        <Button onClick={()=> navigate("/account/register")}>
          register
        </Button>
        </Typography>
      
    </div>
  )
}
