import { Card, Chip, IconButton } from "@mui/material";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import React from "react";

export default function RestaurantCard({ restaurant }) {
  const {
    name,
    description,
    image,
    isOpen,
    ratings,
    cuisineType,
  } = restaurant;

  return (
    <Card
      className="rounded-4xl overflow-hidden
             transition-transform duration-300 ease-in-out
             hover:-translate-y-1 hover:scale-[1.02]
             hover:shadow-lg text-stone-900">
      {/* Image */}
      <div className="relative">
        <img
          src={image}
          alt={name}
          className="h-44 w-full object-cover"
        />

        <Chip
          size="small"
          label={isOpen ? "Open" : "Closed"}
          color={isOpen ? "success" : "error"}
          className="absolute top-2 left-2"
        />
      </div>

      {/* Content */}
      <div className="p-4 space-y-1 bg-gradient-to-br from-orange-100 to-rose-100">
        <div className="flex justify-between items-start">
          <h3 className="font-semibold text-lg text-stone-900 leading-tight">
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