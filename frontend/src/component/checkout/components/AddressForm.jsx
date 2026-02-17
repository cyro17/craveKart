import { Box, IconButton, Modal, TextField, Typography } from "@mui/material";

import React from "react";
import * as Yup from "yup";
import CloseIcon from "@mui/icons-material/Close";
import { ErrorMessage, Form, Formik, useField } from "formik";
import Field from "../../UI/FIeld";
import Button from "../../UI/Button";
import { useDispatch } from "react-redux";
import { postAddress } from "../../../State/address/addressThunk";
import { setSelectedAddress } from "../../../State/address/addressSlice";
import FormikTextField from "../../UI/FIeld";
import { redirect } from "react-router-dom";

const validationSchema = Yup.object({
    firstName: Yup.string().required("First Name is required"),
    lastName: Yup.string().required("Last name is required"),
    streetAddress: Yup.string().required("Street address is required"),
    landmark: Yup.string(),
    city: Yup.string().required("City is required"),
    state: Yup.string().required("State is required"),
    zipCode: Yup.string().required("Postal code is required"),
    country: Yup.string().required("Country is required"),
    deliveryInstruction: Yup.string(),
    isDefault: Yup.boolean(),
});

export default function AddressForm({ isOpen, onClose, onSave }) {
    const dispatch = useDispatch();

    if (!isOpen) return null;

    const initialValues = {
        firstName: "",
        lastName: "",
        streetAddress: "",
        landmark: "",
        city: "",
        state: "",
        zipCode: "",
        country: "",
        deliveryInstruction: "",
    };

    const handleSubmit = async (values) => {
        console.log("submitted values: ", values);
        try {
            const res = await dispatch(postAddress(values)).unwrap();
            console.log("response: ", res);
            dispatch(setSelectedAddress(res));
            onClose();
            redirect("/checkout");
        } catch (err) {
            console.log("Failed to save address: ", err);
        } finally {
        }
    };

    return (
        <Modal
            open={isOpen}
            onClose={onClose}
            closeAfterTransition
            BackdropProps={{
                style: {
                    backgroundColor: "rgba(0, 0, 0, 0.7)",
                    backdropFilter: "blur(4px)",
                    position: "fixed",
                },
            }}
        >
            <Box
                sx={{
                    position: "absolute",
                    top: "50%",
                    left: "50%",
                    transform: "translate(-50%, -50%)",
                    width: { xs: "90%", sm: 500, md: 750 },
                    maxHeight: "90vh",
                    borderRadius: 4,
                    boxShadow: 24,
                    p: 4,
                    overflowY: "hidden",
                    bgcolor: "#ffffff",
                    display: "flex",
                    flexDirection: "column",
                }}
            >
                {/* Header */}
                <div className="flex justify-between items-center px-4 py-3 border-b mb-2 pb-2 text-black">
                    <Typography className="font-bold">
                        Add New Address
                    </Typography>
                    <CloseIcon onClick={onClose} />
                </div>

                {/* Form Body */}
                <div className="overflow-y-auto px-6 py-6 flex-1">
                    <Formik
                        initialValues={initialValues}
                        validationSchema={validationSchema}
                        onSubmit={handleSubmit}
                    >
                        <Form>
                            <div className="space-y-3">
                                {/* First name + last name */}
                                <div className="grid xs:grid-col-1 sm:grid-cols-2 gap-3 mb-2">
                                    <FormikTextField
                                        name="firstName"
                                        label="First Name"
                                    />
                                    <FormikTextField
                                        className
                                        name="lastName"
                                        label="Last Name"
                                    />
                                </div>

                                {/* Street Address */}
                                <div>
                                    <FormikTextField
                                        fullWidth
                                        name="streetAddress"
                                        label="street Address"
                                    />
                                </div>
                                <div>
                                    <FormikTextField
                                        name="landmark"
                                        label="Landmark (Optional)"
                                    />
                                </div>
                                {/* city and state */}
                                <div className="grid xs:grid-col-1 sm:grid-cols-2 gap-3 mb-2">
                                    <FormikTextField name="city" label="City" />
                                    <FormikTextField
                                        className
                                        name="state"
                                        label="State"
                                    />
                                </div>
                                {/* postal code + country */}
                                <div className="grid xs:grid-col-1 sm:grid-cols-2 gap-3">
                                    <FormikTextField
                                        name="zipCode"
                                        label="Zipcode"
                                    />
                                    <FormikTextField
                                        name="country"
                                        label="country"
                                    />
                                </div>
                                {/* <FormikTextField
                                    variant="standard"
                                    name="deliveryInstruction"
                                    label="Delivery Instruction"
                                    multiline
                                    rows={3}
                                /> */}
                            </div>
                            <div className="flex justify-end gap-3 px-6 py-4 border-t bg-white">
                                <Button
                                    type="button"
                                    variant="contained"
                                    sx={{
                                        backgroundColor: "#2a2a2a",
                                        "&:hover": {
                                            backgroundColor: "#3a3a3a",
                                        },
                                    }}
                                >
                                    Cancel
                                </Button>

                                <Button type="submit">Save</Button>
                            </div>
                        </Form>
                    </Formik>
                </div>
            </Box>
        </Modal>
    );
}
