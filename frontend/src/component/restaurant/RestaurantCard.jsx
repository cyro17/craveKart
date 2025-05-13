import { Card, Chip, IconButton } from '@mui/material';
import React from 'react';
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
// import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';


export default function RestaurantCard({data, index}) {
  const auth = useSelector(store => store.auth);
  const jwt = localStorage.getItem("jwt");
  const dispatch = useDispatch();
  const navigate = useNavigate();
  console.log(data);

  // const navigateToRestaurant = () => {
  //   if (data.open) navigate(`/restaurant/${data.address.city}/${data.name}/${data.id}`);
  // }

  // const handleAddToFavorites = () => {
  //   if (data.open)
  //     navigate(`/restaurant/${data.address.city}/${data.name}/${data.id}`);
  // }

  return (
    <Card className='w-[18rem] mb-5 mx-1'>
      <div
        // onClick={navigateToRestaurant}
        className={`${data.open ? 'cursor-pointer' : 'cursore-not-allowed'} relative`}>
        <img className='w-full h-[10rem] rounded-t-md'
          src={data.images[0]}
          alt="indian fast food" />
        <Chip size='small'
          className='absolute top-2 left-2'
          color={data.open ? "success" : "error"}
          label={data.open ? "Open" : "Closed"} />
      </div>

      <div className='p-4 textPart lg:flex w-full justify-between'>
        <div className='space-y-1'>
          <p className='font-semibold text-lg'>{data.name}</p>
          <p className='text-gray-500 text-sm'>
            {
              data.description.length > 40 ?
                data.description.substring(0, 40) + "..." : data.description
            }
          </p>
        </div>

        <div>
          <IconButton
            // onClick={handleAddToFavorites}
          >
            {true? <FavoriteIcon/> : <FavoriteBorderIcon/>}
          </IconButton>
        </div>
      </div>

    </Card>
  )
}
