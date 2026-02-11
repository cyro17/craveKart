import React, { useState, useEffect } from "react";
import {
  Box,
  TextField,
  Button,
  Typography,
  IconButton,
  InputAdornment,
  CircularProgress,
} from "@mui/material";
import { Visibility, VisibilityOff } from "@mui/icons-material";
import { useDispatch, useSelector } from "react-redux";
import { registerUser } from "../../State/Authentication/AuthThunks";

export default function RegisterPage({ onSuccess, switchMode }) {
  const dispatch = useDispatch();
  const { isLoading, error } = useSelector((state) => state.auth);

  const [showPassword, setShowPassword] = useState(false);
  const [values, setValues] = useState({
    fullName: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [formError, setFormError] = useState("");
  const [isValid, setIsValid] = useState(false);

  // Basic validation
  useEffect(() => {
    const emailValid = values.email.includes("@");
    const passwordValid = values.password.length >= 6;
    const match = values.password === values.confirmPassword;
    const nameValid = values.fullName.trim().length > 2;

    setIsValid(emailValid && passwordValid && match && nameValid);

    if (!match && values.confirmPassword.length > 0) {
      setFormError("Passwords do not match");
    } else {
      setFormError("");
    }
  }, [values]);

  const handleChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!isValid) return;

    dispatch(registerUser(values))
      .unwrap()
      .then(() => {
        onSuccess(); // close drawer
      })
      .catch(() => {});
  };

  return (
    <Box component="form" onSubmit={handleSubmit}>
      {/* Heading */}
      <Typography variant="h4" fontWeight="700" mb={1}>
        Create Account 🚀
      </Typography>

      <Typography variant="body2" color="gray" mb={4}>
        Join CraveKart and start ordering your favorite meals.
      </Typography>

      {/* Full Name */}
      <TextField
        fullWidth
        name="fullName"
        label="Full Name"
        margin="normal"
        value={values.fullName}
        onChange={handleChange}
        autoFocus
        sx={{
          "& .MuiOutlinedInput-root": {
            backgroundColor: "rgba(255,255,255,0.05)",
            borderRadius: "12px",
          },
        }}
      />

      {/* Email */}
      <TextField
        fullWidth
        name="email"
        label="Email Address"
        margin="normal"
        value={values.email}
        onChange={handleChange}
        sx={{
          "& .MuiOutlinedInput-root": {
            backgroundColor: "rgba(255,255,255,0.05)",
            borderRadius: "12px",
          },
        }}
      />

      {/* Password */}
      <TextField
        fullWidth
        name="password"
        type={showPassword ? "text" : "password"}
        label="Password"
        margin="normal"
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

      {/* Confirm Password */}
      <TextField
        fullWidth
        name="confirmPassword"
        type={showPassword ? "text" : "password"}
        label="Confirm Password"
        margin="normal"
        value={values.confirmPassword}
        onChange={handleChange}
        error={!!formError}
        helperText={formError}
        sx={{
          "& .MuiOutlinedInput-root": {
            backgroundColor: "rgba(255,255,255,0.05)",
            borderRadius: "12px",
          },
        }}
      />

      {/* Backend Error */}
      {error && (
        <Typography color="error" fontSize="14px" mt={2}>
          {error}
        </Typography>
      )}

      {/* Submit Button */}
      <Button
        fullWidth
        type="submit"
        disabled={!isValid || isLoading}
        variant="contained"
        sx={{
          mt: 3,
          py: 1.3,
          borderRadius: "12px",
          fontWeight: "bold",
          fontSize: "1rem",
          background: "linear-gradient(90deg, #FF6B35, #FF914D)",
          boxShadow: "0 6px 20px rgba(255,107,53,0.4)",
          transition: "0.3s",
          "&:hover": {
            transform: "translateY(-2px)",
          },
        }}
      >
        {isLoading ? (
          <CircularProgress size={22} color="inherit" />
        ) : (
          "Create Account"
        )}
      </Button>

      {/* Switch to Login */}
      <Typography
        mt={4}
        textAlign="center"
        sx={{
          cursor: "pointer",
          color: "#FF6B35",
          "&:hover": { textDecoration: "underline" },
        }}
        onClick={switchMode}
      >
        Already have an account? Login
      </Typography>
    </Box>
  );
}