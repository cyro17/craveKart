import React from 'react';
import { Button, FormControl, InputLabel, MenuItem, Select, TextField, Typography } from '@mui/material'
import { Field, Formik, Form } from 'formik'; 
import { useDispatch } from 'react-redux';
import { healthCheck, registerUser } from '../../State/Authentication/Action';
import * as Yup from 'yup';
import { useNavigate } from 'react-router-dom';


const initialValues = {
  fullName:"", 
  email: "", 
  password: "",
  role: ""
}

const validationSchema = Yup.object({
  fullName: Yup.string().required("Full Name is required"),
  email: Yup.string()
    .email("Invalid email format")
    .required("Email is required"),
  password: Yup.string()
    .required("Password is required"),
});



export default function RegisterForm() {

  const navigate = useNavigate();
  const dispatch = useDispatch();
  

  const handleSubmit = (values) => {
    console.log("submit handler : ", values);
    dispatch(registerUser({userData: values, navigate}))
  }

  const handleHealthCheck = () => {
    dispatch(healthCheck());
  }

  return (
    <div>
      <Typography variant='h5' className='text-center'>
        Register 
      </Typography>
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
      >
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
          <Button onClick={handleHealthCheck}> health Check</Button>
          <Button sx={{ mt: 2, padding: "1rem " }}
            fullWidth
            type='submit'
            variant='contained'
          >
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
