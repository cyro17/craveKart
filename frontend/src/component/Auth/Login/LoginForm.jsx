
import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import { Field, Form, Formik } from 'formik';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { loginUser } from '../../../State/Authentication/actions';
import { Alert, Snackbar } from '@mui/material';
import { clearAuthState } from '../../../State/Authentication/authSlice';
import { useState, useEffect } from 'react';

const initialValues = {
  email: "", 
  password: "",
}

export default function LoginForm() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const auth = useSelector(state => state.auth);

  console.log("authenticated user: ", auth.user);

  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [snackbarSeverity, setSnackbarSeverity] = useState("success");

  useEffect(() => {
    if (auth.success) {
      setSnackbarMessage(auth.success);
      setSnackbarSeverity("success");
      setOpenSnackbar(true);
    }

    if (auth.success === "Login Success") {
      setTimeout(() => {
        navigate("/");
      }, 1500);
    }


    if (auth.error) {
      setSnackbarMessage(auth.error);
      setSnackbarSeverity("error");
      setOpenSnackbar(true);
      dispatch(clearAuthState());
    }
  }, [auth.success, auth.error, navigate, dispatch]);

  function handleCloseSnackbar() {
    setOpenSnackbar(false);
  }

  const handleSubmit = (values) => {
    
    dispatch(loginUser({ values, navigate }));

  }


  return (
    <div>
      <Typography variant='h5' className='text-center'>
        Login 
      </Typography>
      <Formik
        initialValues={initialValues}
        onSubmit={handleSubmit}>
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
      
      {/* Success Snackbar */}
      <Snackbar
        open={openSnackbar}
        autoHideDuration={2000}
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
      >
        <Alert onClose={handleCloseSnackbar} severity={snackbarSeverity}
          sx={{ width: '100%' }}>
            {snackbarMessage}
        </Alert>
      </Snackbar>
      
    </div>
  )
}


// // import ForgotPassword from './components/ForgotPassword';
// // import AppTheme from '../shared-theme/AppTheme';
// // import ColorModeSelect from '../shared-theme/ColorModeSelect';
// // import { GoogleIcon, FacebookIcon, SitemarkIcon } from './components/CustomIcons';

// const Card = styled(MuiCard)(({ theme }) => ({
//   display: 'flex',
//   flexDirection: 'column',
//   alignSelf: 'center',
//   width: '100%',
//   padding: theme.spacing(4),
//   gap: theme.spacing(2),
//   margin: 'auto',
//   [theme.breakpoints.up('sm')]: {
//     maxWidth: '450px',
//   },
//   boxShadow:
//     'hsla(220, 30%, 5%, 0.05) 0px 5px 15px 0px, hsla(220, 25%, 10%, 0.05) 0px 15px 35px -5px',
//   ...theme.applyStyles('dark', {
//     boxShadow:
//       'hsla(220, 30%, 5%, 0.5) 0px 5px 15px 0px, hsla(220, 25%, 10%, 0.08) 0px 15px 35px -5px',
//   }),
// }));

// const SignInContainer = styled(Stack)(({ theme }) => ({
//   height: 'calc((1 - var(--template-frame-height, 0)) * 100dvh)',
//   minHeight: '100%',
//   padding: theme.spacing(2),
//   [theme.breakpoints.up('sm')]: {
//     padding: theme.spacing(4),
//   },
//   '&::before': {
//     content: '""',
//     display: 'block',
//     position: 'absolute',
//     zIndex: -1,
//     inset: 0,
//     backgroundImage:
//       'radial-gradient(ellipse at 50% 50%, hsl(210, 100%, 97%), hsl(0, 0%, 100%))',
//     backgroundRepeat: 'no-repeat',
//     ...theme.applyStyles('dark', {
//       backgroundImage:
//         'radial-gradient(at 50% 50%, hsla(210, 100%, 16%, 0.5), hsl(220, 30%, 5%))',
//     }),
//   },
// }));

// export default function LoginForm(props) {
//   const [emailError, setEmailError] = React.useState(false);
//   const [emailErrorMessage, setEmailErrorMessage] = React.useState('');
//   const [passwordError, setPasswordError] = React.useState(false);
//   const [passwordErrorMessage, setPasswordErrorMessage] = React.useState('');
//   const [open, setOpen] = React.useState(false);

//   const handleClickOpen = () => {
//     setOpen(true);
//   };

//   const handleClose = () => {
//     setOpen(false);
//   };

//   const handleSubmit = (event) => {
//     if (emailError || passwordError) {
//       event.preventDefault();
//       return;
//     }
//     const data = new FormData(event.currentTarget);
//     console.log({
//       email: data.get('email'),
//       password: data.get('password'),
//     });
//   };

//   const validateInputs = () => {
//     const email = document.getElementById('email');
//     const password = document.getElementById('password');

//     let isValid = true;

//     if (!email.value || !/\S+@\S+\.\S+/.test(email.value)) {
//       setEmailError(true);
//       setEmailErrorMessage('Please enter a valid email address.');
//       isValid = false;
//     } else {
//       setEmailError(false);
//       setEmailErrorMessage('');
//     }

//     if (!password.value || password.value.length < 6) {
//       setPasswordError(true);
//       setPasswordErrorMessage('Password must be at least 6 characters long.');
//       isValid = false;
//     } else {
//       setPasswordError(false);
//       setPasswordErrorMessage('');
//     }

//     return isValid;
//   };

//   return (
//     <Box {...props}>
//       <CssBaseline enableColorScheme />
//       <SignInContainer direction="column" justifyContent="space-between">
//         <Box sx={{ position: 'fixed', top: '1rem', right: '1rem' }} />
//         <Card variant="outlined">
//           <SitemarkIcon />
//           <Typography
//             component="h1"
//             variant="h4"
//             sx={{ width: '100%', fontSize: 'clamp(2rem, 10vw, 2.15rem)' }}
//           >
//             Sign in
//           </Typography>
//           <Box
//             component="form"
//             onSubmit={handleSubmit}
//             noValidate
//             sx={{
//               display: 'flex',
//               flexDirection: 'column',
//               width: '100%',
//               gap: 2,
//             }}
//           >
//             <FormControl>
//               <FormLabel htmlFor="email">Email</FormLabel>
//               <TextField
//                 error={emailError}
//                 helperText={emailErrorMessage}
//                 id="email"
//                 type="email"
//                 name="email"
//                 placeholder="your@email.com"
//                 autoComplete="email"
//                 autoFocus
//                 required
//                 fullWidth
//                 variant="outlined"
//                 color={emailError ? 'error' : 'primary'}
//               />
//             </FormControl>
//             <FormControl>
//               <FormLabel htmlFor="password">Password</FormLabel>
//               <TextField
//                 error={passwordError}
//                 helperText={passwordErrorMessage}
//                 name="password"
//                 placeholder="••••••"
//                 type="password"
//                 id="password"
//                 autoComplete="current-password"
//                 autoFocus
//                 required
//                 fullWidth
//                 variant="outlined"
//                 color={passwordError ? 'error' : 'primary'}
//               />
//             </FormControl>
//             <FormControlLabel
//               control={<Checkbox value="remember" color="primary" />}
//               label="Remember me"
//             />
//             <ForgotPassword open={open} handleClose={handleClose} />
//             <Button
//               type="submit"
//               fullWidth
//               variant="contained"
//               onClick={validateInputs}
//             >
//               Sign in
//             </Button>
//             <Link
//               component="button"
//               type="button"
//               onClick={handleClickOpen}
//               variant="body2"
//               sx={{ alignSelf: 'center' }}
//             >
//               Forgot your password?
//             </Link>
//           </Box>
//           <Divider>or</Divider>
//           <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
//             <Button
//               fullWidth
//               variant="outlined"
//               onClick={() => alert('Sign in with Google')}
//               startIcon={<GoogleIcon />}
//             >
//               Sign in with Google
//             </Button>
//             <Button
//               fullWidth
//               variant="outlined"
//               onClick={() => alert('Sign in with Facebook')}
//               startIcon={<FacebookIcon />}
//             >
//               Sign in with Facebook
//             </Button>
//             <Typography sx={{ textAlign: 'center' }}>
//               Don&apos;t have an account?{' '}
//               <Link
//                 href="/material-ui/getting-started/templates/sign-in/"
//                 variant="body2"
//                 sx={{ alignSelf: 'center' }}
//               >
//                 Sign up
//               </Link>
//             </Typography>
//           </Box>
//         </Card>
//       </SignInContainer>
//     </Box>
//   );
// }