import React from 'react';
import { Button, FormControl, InputLabel, MenuItem, Select, TextField, Typography } from '@mui/material'
import { Field, Formik } from 'formik'
import { Form, useNavigate } from 'react-router-dom';


const initialValues = {
  fullName:"", 
  email: "", 
  password: "",
  role: ""
}

export default function RegisterForm() {
  const navigate = useNavigate();

  const handleSubmit = (values) => {
    console.log("values", values  )
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
              name="fullName"
              label="Full Name"
              fullWidth
              variant="outlined"
              margin="normal"
          />
          <Field
              as={TextField}
              name="email"
              label="Email"
              fullWidth
              variant="outlined"
              margin="normal"
            />
            <Field
              as={TextField}
              name="password"
              label="Password"
              type="password"
              fullWidth
              variant="outlined"
              margin="normal"
          />
           <Field name="role">
              {({ field }) => (
                <FormControl fullWidth margin="normal">
                  <InputLabel id="role-simple-select-label">Role</InputLabel>
                  <Select {...field} labelId="role-simple-select-label">
                    <MenuItem value="ROLE_CUSTOMER">Customer</MenuItem>
                    <MenuItem value="ROLE_RESTAURANT_OWNER">Restaurant Owner</MenuItem>
                  </Select>
                </FormControl>
              )}
            </Field>
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
