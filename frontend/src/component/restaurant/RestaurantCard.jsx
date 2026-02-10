import { Card, Chip, IconButton } from "@mui/material";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import React from "react";

export default function RestaurantCard({ restaurant }) {
  const {
    name,
    description,
    image,
    open,
    ratings,
    cuisineType,
  } = restaurant;

  return (
    <Card className="rounded-xl overflow-hidden hover:shadow-xl transition-all duration-300">
      {/* Image */}
      <div className="relative">
        <img
          src={image}
          alt={name}
          className="h-44 w-full object-cover"
        />

        <Chip
          size="small"
          label={open ? "Open" : "Closed"}
          color={open ? "success" : "error"}
          className="absolute top-2 left-2"
        />
      </div>

      {/* Content */}
      <div className="p-4 space-y-1">
        <div className="flex justify-between items-start">
          <h3 className="font-semibold text-lg leading-tight">
            {name}
          </h3>
          <IconButton size="small">
            <FavoriteBorderIcon />
          </IconButton>
        </div>

        <p className="text-sm text-gray-500">
          {cuisineType}
        </p>

        <p className="text-sm text-gray-600 line-clamp-2">
          {description}
        </p>

        {/* Rating */}
        <div className="flex items-center gap-2 text-sm mt-2">
          <span className="bg-green-600 text-white px-2 py-[2px] rounded-md">
            ⭐ {ratings || "New"}
          </span>
        </div>
      </div>
    </Card>
  );
}