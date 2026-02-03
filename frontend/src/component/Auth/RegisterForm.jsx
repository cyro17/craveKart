import React from 'react';
import { Button, FormControl, InputLabel, MenuItem, Select, TextField, Typography } from '@mui/material'
import { Field, Formik, Form } from 'formik'; 
import { useDispatch } from 'react-redux';
import { healthCheck, registerUser } from '../../State/Authentication/actions';
import * as Yup from 'yup';
import { useNavigate } from 'react-router-dom';


const initialValues = {
  username: "", 
  firstName: "",
  lastName: "",
  email: "",
  password: "",
  role: "",
  contactInfo: {
    mail: "", mobile: "", twitter: "", instagram: ""
  }
}

const validationSchema = Yup.object({
  username: Yup.string().required("Username is required"),
  firstName: Yup.string().required("First name is required"),
  lastName: Yup.string().required("Last name is required"),
  email: Yup.string()
    .email("Invalid email format")
    .required("Email is required"),
  password: Yup.string().required("Password is required"),
  role: Yup.string().required("Role is required"),
  contactInfo: Yup.object({
    mail: Yup.string().email("Invalid email"),
    mobile: Yup.string().matches(/^[0-9]{10}$/, "Mobile must be 10 digits")
  })
});



export default function RegisterForm() {

  const navigate = useNavigate();
  const dispatch = useDispatch();
  

  const handleSubmit = (values) => {
    const payload = {
      ...values, 
      // role: values.role.toUpperCase()
    }
    console.log("Register payload: ", payload);
    dispatch(registerUser({userData: payload, navigate}));
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
              name="username"
              label="Username"
              fullWidth
              variant="outlined"
              margin="normal"
          />
          <Field
              as={TextField}
              name="firstName"
              label="First Name"
              fullWidth
              variant="outlined"
              margin="normal"
            />
          <Field
              as={TextField}
              name="lastName"
              label="Last Name"
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
                    <MenuItem value="CUSTOMER">Customer</MenuItem>
                    <MenuItem value="RESTAURANT_PARTNER">Restaurant Partner</MenuItem>
                    <MenuItem value="ADMIN">ADMIN</MenuItem>
                  </Select>
                </FormControl>
              )}
          </Field>
          <Button onClick={handleHealthCheck}> health Check</Button>
          <Button sx={{ mt: 2, padding: "1rem " }}
            fullWidth
            type='submit'
            variant='contained'
            // onClick={()=> handleHealthCheck()}
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
