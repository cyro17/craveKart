import { Button as MuiButton } from "@mui/material";

export default function Button({
  children,
  variant = "contained",
  size = "medium",
  sx = {},
  ...props
}) {
  return (
    <MuiButton
      variant={variant}
      size={size}
      sx={{
        textTransform: "none",
        borderRadius: size === "large" ? "16px" : "12px",
        fontWeight: 600,
        ...sx,
      }}
      {...props}
    >
      {children}
    </MuiButton>
  );
}