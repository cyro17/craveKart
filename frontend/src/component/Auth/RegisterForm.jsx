import React from 'react';
import { Button, TextField, Typography } from '@mui/material'
import { Field, Formik } from 'formik'
import { Form, useNavigate } from 'react-router-dom'



const initialValues = {
  fullName:"", 
  email: "", 
  password: "",
  role: "ROLE_CUSTOMER"
}

export default function RegisterForm() {
  const navigate = useNavigate();

  const handleSubmit = () => {
    console.log("handleSubmit")
  }
  return (
    <div>
      <Typography variant='h5' className='text-center'>
        Register 
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
              Register
            </Button>
          </Form>
        </Formik>
      <Typography variant='body2' align='center' sx={{mt:3}}>
        Already have account ?
        <Button onClick={()=> navigate("/account/login")}>
          login 
        </Button>
        </Typography>
      
    </div>
  )
}
