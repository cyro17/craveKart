import React, { useEffect, useRef, useState } from 'react'
import Button from '../../UI/Button';
import { motion } from "framer-motion";
import { Search, MapPin, Star, Truck } from "lucide-react";
// import { Card, CardContent } from "@/components/ui/card";
import { Input } from '@mui/material';
import { useNavigate, useParams, useSearchParams } from 'react-router-dom';


export default function Hero() {
  const navigate = useNavigate();
  const { city } = useParams();
  
  const [searchParams] = useSearchParams();
  const [locationInput, setLocationInput] = useState(city || "");  
  const [searchInput, setSearchInput] = useState(searchParams.get("q") || "");

  useEffect(() => { 
    if (city) setLocationInput(city);
    else setLocationInput("");
  }, [city])

  const handleSearch = () => {
    const selectedCity = locationInput.trim();
    if (!selectedCity) return;

    const params = new URLSearchParams();
    if (searchInput) params.set("q", searchInput);
    navigate(`/${selectedCity}?${params?.toString()}`)
  }

  const handleCategoryClick = (category) => {
    navigate(`/${city}?cuisine=${category.toLowerCase()}`);
  }

  return (
    <section className="relative overflow-hidden bg-gradient-to-br from-orange-50 via-white to-orange-100">
    <div className="mx-auto max-w-7xl px-6 py-20 grid grid-cols-1 md:grid-cols-2 gap-12 items-center">
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
      >
        <h1 className="text-4xl md:text-5xl font-bold text-stone-900 leading-tight">
          Discover the best food in {" "}
            <span className="block text-orange-600 capitalize">{city}</span>
        </h1>
        <p className="mt-4 text-neutral-600 text-lg">
          Search restaurants, dishes, or stores near you
        </p>

        {/* Search Bar */}
        <div className="mt-8 flex items-center gap-3 bg-white text-stone-900 shadow-lg rounded-2xl p-3">
          <MapPin className="text-orange-500" />
          <Input
              value={locationInput}
              autoFocus={!city}
              onChange={(e)=> setLocationInput(e.target.value)}
              placeholder="city"
              className="border-none text-black focus-visible:ring-0"
              sx={{
                color: "black"
              }}
          />

          <div className="h-6 w-px bg-neutral-200" />
          
          <Search className="text-neutral-400" />
          <Input
            value={searchInput}
            onChange={(e)=> setSearchInput(e.target.value)}
            placeholder="Search for food or restaurants"
            className="border-none focus-visible:ring-0"
            onKeyDown={(e) => {
              if (e.key === "Enter") handleSearch();
            }}
            sx={{
              color: "black"
            }}
          />
          <Button
            onClick={()=>handleSearch()}
            className="rounded-xl bg-orange-500 hover:bg-orange-600">
            Search
          </Button>
        </div>

        {/* Category Chips */}
        <div className="mt-6 flex flex-wrap gap-3">
          {["Pizza", "Burger", "Biryani", "Cafe", "Desserts"].map((cat) => (
            <Button
              onClick={()=>handleCategoryClick(cat)}
              key={cat}
              variant="outline"
              className="rounded-full"
            >
              {cat}
            </Button>
          ))}
        </div>
      </motion.div>

      {/* Hero Visual */}
      <motion.img
          src=
          "https://images.unsplash.com/photo-1496116218417-1a781b1c416c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTR8fGZvb2R8ZW58MHwwfDB8fHww"
        alt="Food delivery"
        initial={{ opacity: 0, scale: 0.95 }}
        animate={{ opacity: 1, scale: 1 }}
        transition={{ duration: 0.6 }}
        className="w-full h-[25vh] rounded-3xl shadow-xl object-fill"
      />
    </div>
  </section>
    
  )
}
