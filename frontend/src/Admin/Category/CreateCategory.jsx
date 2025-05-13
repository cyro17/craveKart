import { Button, TextField } from '@mui/material'
import { Formik } from 'formik'
import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { Form, useParams } from 'react-router-dom';
import { createCategory } from '../../State/Customers/Restaurant/actions';
import Auth from '../../component/Auth/Auth';


export default function CreateCategory({ handleClose }) {
  const { id } = useParams();
  const { auth, restaurant } = useSelector(store => store);
  const jwt = localStorage.getItem("jwt");

  const [formData, setFormData] = useState({
    categoryName: "",
    restaurantId: ""
  });

  const dispatch = useDispatch();

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData({
      ...formData,
      [name]: value,
    })
  }

  const handleFormSubmit = (event) => {
    event.preventDefault();
    const data = {
      name: formData.categoryName,
      restaurantId: {
        id
      }
    }

    dispatch(createCategory({
      reqData: data, jwt: auth.jwt || jwt
    }));

    setFormData({
      categoryName: "",
      restaurantId: ""
    });

    handleClose();
    console.log("form submitted: ", formData);
  }

  return (
    <div>
      <div>
        <h1>CreateCategory</h1>
        <form action="">
          <TextField
            label="category name"
            name='categoryName'
            value={formData.categoryName}
            onChange={handleInputChange}
            fullWidth
          />
          <Button type='submit' variant='contained' color='primary'>
            Create
          </Button>
        </form>
      </div>
    </div>
  )
}
