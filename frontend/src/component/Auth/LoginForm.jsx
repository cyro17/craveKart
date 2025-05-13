import React from 'react';
import { Button, TextField, Typography } from '@mui/material'
import {  useNavigate } from 'react-router-dom'
import { Field, Formik, Form } from 'formik'
import { useDispatch } from 'react-redux';
import { loginUser } from '../../State/Authentication/actions';
import {motion} from 'motion/react'

const initialValues = {
  email: "", 
  password: "",
}
// const MotionButton = motion.create(Button);

export default function LoginForm() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  
  const handleSubmit = (values) => {
    console.log("values", values);
    dispatch(loginUser({data: values, navigate}))  
    
  }
  return (
    <div>
      <Typography variant='h5' className='text-center'>
        Login 
      </Typography>
        <Formik initialValues={initialValues} onSubmit={handleSubmit}>
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
          >
              Login
            </Button>
          </Form>
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
