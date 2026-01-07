import { Avatar, Card, CardHeader, Chip, IconButton } from '@mui/material';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import React from 'react';
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
// import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';


export default function RestaurantCard({data}) {
  const navigate = useNavigate();
  const { auth } = useSelector((store) => store);
  const jwt=localStorage.getItem("jwt");

  const dispatch = useDispatch();

  // const handleAddToFavorites = () => {
  //   dispatch(addTo({restaurantId:data.id,jwt:auth.jwt||jwt}));
  // };

  const navigateToRestaurant = () => {
    if(data.open)
    navigate(`/restaurant/${data.address.city}/${data.name}/${data.id}`);
  };


  return (
    <Card className="m-5 w-[18rem] productCard ">
    <div onClick={navigateToRestaurant} className={`${data.open?"cursor-pointer":"cursor-not-allowed"}  relative`}>
      <img
        className="w-full h-[10rem] rounded-t-md object-cover "
        src={data.images[0]}
        alt=""
      />
      <Chip
        size="small"
        // variant="outlined"
        className="absolute top-2 left-2"
        color={data.open?"success":"error"}
        label={data.open ? "Open" : "Closed"}
      />
    </div>
    <div className="p-4 textPart lg:flex w-full justify-between">
      <div className="space-y-1">
        <p className="font-semibold text-lg">{data.name}</p>
        {/* <div>
        <span>{data.rating}</span>
      </div> */}
        <p className="text-gray-500 text-sm">
          {data.description.length > 40
            ? data.description.substring(0, 40) + "..."
            : data.description}
        </p>
      </div>

      <div>
        <IconButton >
          {/* {isPresentInFavorites(auth.favorites, data) ? (
            <FavoriteIcon color="primary" />
          ) : (
            <FavoriteBorderIcon />
          )} */}
        </IconButton>
      </div>
    </div>
  </Card>
  )
}
