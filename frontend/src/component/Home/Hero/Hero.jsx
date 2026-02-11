import React, { useState } from 'react'
import Button from '../../UI/Button';
import { motion } from "framer-motion";
import { Search, MapPin, Star, Truck } from "lucide-react";
// import { Card, CardContent } from "@/components/ui/card";
import { Input } from '@mui/material';


export default function Hero() {
    const [location, setLocation] = useState("");   
  return (
    <section className="relative overflow-hidden bg-gradient-to-br from-orange-50 via-white to-orange-100">
    <div className="mx-auto max-w-7xl px-6 py-20 grid grid-cols-1 md:grid-cols-2 gap-12 items-center">
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
      >
        <h1 className="text-4xl md:text-5xl font-bold text-neutral-900 leading-tight">
          Find your cravings.
          <span className="block text-orange-600">Delivered fast.</span>
        </h1>
        <p className="mt-4 text-neutral-600 text-lg">
          Search restaurants, dishes, or stores near you
        </p>

        {/* Search Bar */}
        <div className="mt-8 flex items-center gap-3 bg-white text-stone-900 shadow-lg rounded-2xl p-3">
          <MapPin className="text-orange-500" />
          <Input
            placeholder="Enter delivery location"
            className="border-none focus-visible:ring-0"
          />
          <div className="h-6 w-px bg-neutral-200" />
          <Search className="text-neutral-400" />
          <Input
            placeholder="Search for food or restaurants"
            className="border-none focus-visible:ring-0"
          />
          <Button className="rounded-xl bg-orange-500 hover:bg-orange-600">
            Search
          </Button>
        </div>

        {/* Category Chips */}
        <div className="mt-6 flex flex-wrap gap-3">
          {["Pizza", "Burger", "Biryani", "Cafe", "Desserts"].map((cat) => (
            <Button
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
