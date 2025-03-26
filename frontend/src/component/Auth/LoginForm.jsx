import React from 'react';
import { Button, TextField, Typography } from '@mui/material'
import { Field, Formik } from 'formik'
import { Form, useNavigate } from 'react-router-dom'

const initialValues = {
  email: "", 
  password: "",
}

export default function LoginForm() {
  const navigate = useNavigate();
  
  const handleSubmit = () => {
    console.log("handleSubmit")
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
            <Button sx={{mt: 2, padding: "1rem "}} fullWidth type='submit' variant='contained'>
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
