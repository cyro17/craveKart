import { Card as MuiCard, CardContent as MuiCardContent } from "@mui/material";

export function Card({ children, sx = {}, ...props }) {
  return (
    <MuiCard
      sx={{
        borderRadius: "24px",
        boxShadow:
          "0 20px 40px rgba(0, 0, 0, 0.12)",
        ...sx,
      }}
      {...props}
    >
      {children}
    </MuiCard>
  );
}

export function CardContent({ children, sx = {}, ...props }) {
  return (
    <MuiCardContent
      sx={{
        padding: "24px",
        "&:last-child": { paddingBottom: "24px" },
        ...sx,
      }}
      {...props}
    >
      {children}
    </MuiCardContent>
  );
}