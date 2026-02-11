import React from 'react';
import { Button, TextField, Typography } from '@mui/material'
import {  useNavigate } from 'react-router-dom'
import { Field, Formik, Form } from 'formik'
import { useDispatch } from 'react-redux';
import { loginUser } from '../../State/Authentication/actions';
import {motion} from 'motion/react'
import { toast } from 'react-toastify';

const initialValues = {
  email: "", 
  password: "",
}
// const MotionButton = motion.create(Button);

export default function LoginForm() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  
  const handleSubmit = async (values, {setSubmitting}) => {
    try {
      const response = await dispatch(loginUser(values));
      console.log("response : ", response);
      if (response?.payload?.token) {
        localStorage.setItem("token", response.payload.token);
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
      console.error(error);
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
