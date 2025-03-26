
import React from 'react';
import CartItem from './CartItem';
import { Button, Card, Divider, Grid2, Modal, TextField } from '@mui/material';
import AddressCard from './AddressCard';
import LocationOnIcon from '@mui/icons-material/LocationOn';

import Box from '@mui/material/Box';
import { ErrorMessage, Field, Form, Formik } from 'formik';
import * as Yup from "yup";

const items = [1, 1, 1, 1, 1, 1];
const addressItem = [1, 1, 1, 1, 1];

export const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    outline: 'none', 
    boxShadow: 24,
    p: 4,
};
  
const initialValues = {
    streetAddress: '',
    state: '',
    pincode: '',
    city: ''
}

const validationSchema = Yup.object().shape({
    streetAddress: Yup.string().required('Street Address is Required'), // Fixed typo in 'Address'
    state: Yup.string().required('State is Required'), 
    pincode: Yup.number()
      .typeError('PinCode must be a number') // Ensures pincode is a number
      .required('PinCode is Required'),
    city: Yup.string().required('City is Required')
  });
  
export default function Cart() {
    const [open, setOpen] = React.useState(false);  
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    function handleSubmit(value) {
        console.log(value);
        // console.log('Order created successfully');
    }

  return (
      <div>
          <main className='lg:flex justify-between'>
              <section className=' lg:w-[30%]space-y-6 lg:min-h-screen pt-10' >
                  <div className='w-full'>
                  {
                      items.map((item) =>
                          <CartItem />)
                  }
                  </div>
                  
                  <Divider />

                  <div className="billDetails px-5 text-sm w-full">
                      <p className='font-extralight py-5'>Bill Details</p>
                      <div className='space-y-3'>
                          <div className='flex justify-between text-gray-400'>
                              <p>Item total</p>
                              <p>599</p>
                          </div>
                          <div className='flex justify-between text-gray-400'>
                              <p>Delivery Charges</p>
                              <p>599</p>
                          </div>
                          <div className='flex justify-between text-gray-400'>
                              <p>Delivery Charges</p>
                              <p>34</p>
                          </div>
                          <div className='flex justify-between text-gray-400'>
                              <p>GST</p>
                              <p>5%</p>
                          </div>
                          <div className='flex justify-between text-gray-400'>
                              <p>Restaurant Charges</p>
                              <p>5%</p>
                          </div>
                          <Divider />
                      </div>
                      <div className='flex justify-between text-gray-400'>
                          <p>Total</p>
                          <p>5999</p>
                      </div>
                  </div>
              </section>
              <Divider orientation='vertical' flexItem/>
              <section className='lg:w-[70%] flex justify-center px-5 pb-10 lg:pb-10'>
                  <div>
                     <h1 className='text-center font-semibold text-2xl py-10'>Choose Delivery Address</h1> 
                     <div className='flex flex-wrap justify-center '>
                          {
                              [1, 1, 1, 1, 1].map((item) => 
                                  <AddressCard item={item} showButton={true}
                                      />)
                          }
                          <Card className='flex gap-5 w-64 p-5 m-5'>
                            <LocationOnIcon />
                            <div className='space-y-3 text-gray-500 '>
                                <div className='font-semibold text-lg text-white'>Add New Address</div>
                                
                                  {
                                      <Button onClick={handleOpen} variant="outlined" fullWidth >
                                        Add
                                    </Button>
                                }
                            </div>
                          </Card>
                    </div>
                  </div>
                  
              </section>
          </main>
          <Modal
            open={open}
            onClose={handleClose}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                  <Formik initialValues={initialValues } validationSchema={ validationSchema} onSubmit={handleSubmit}>
                      <Form>
                      <Grid2 container spacing={2}>
                          <Grid2 className="w-full">
                              <Field as={TextField}
                                  name="streetAddress"
                                  label="Street Address"
                                  fullWidth
                                  variant="outlined"
                              />
                          </Grid2>

                          <Grid2 className='flex gap-5'>
                              <Field as={TextField}
                                  name="city"
                                  label="City"
                                  fullWidth
                                  variant="outlined"
                              />
                              <Field as={TextField}
                                  name="pincode"
                                  label="PinCode"
                                  fullWidth
                                  variant="outlined"
                              />
                          </Grid2>
                            <Grid2  className="w-full">
                              <Field as={TextField}
                                  name="state"
                                  label="State"
                                  fullWidth
                                  variant="outlined"
                              />
                          </Grid2>
                          <Button
                              onSubmit={handleSubmit}
                              type="submit"
                              variant='contained'
                              className='w-full'>
                              Deliver Here
                          </Button>
                      </Grid2>
                      </Form>    
                    </Formik>
                </Box>
            </Modal>
    </div>
  )
}
