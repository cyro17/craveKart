import { TextField } from "@mui/material";
import { useField } from "formik";

export default function FormikTextField(props) {
    const [field, meta] = useField(props);
    // console.log(props);
    // console.log(field);
    // console.log(meta);

    const height = props.height;
    return (
        <TextField
            {...field}
            {...props}
            label={props.label}
            variant={props.variant || "outlined"}
            error={meta.touched && Boolean(meta.error)}
            helperText={meta.touched && meta.error}
            sx={{
                "& .MuiOutlinedInput-root": {
                    borderRadius: 2,
                    height: { height },
                    "& fieldset": { borderColor: "#ccc" },
                    "&:hover fieldset": { borderColor: "#888" },
                    color: "black",
                },
                "& .MuiInputLabel-root": { color: "#555" },
                input: { fontSize: 16, padding: "12px" },
            }}
        />
    );
}
