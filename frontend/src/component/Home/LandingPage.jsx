import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Hero from "../Home/Hero/Hero";
import FoodCategories from "../Home/FoodCategories/FoodCategories";

export default function Landing() {
  const [city, setCity] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!city.trim()) return;

    const formattedCity = city.trim().toLowerCase();
    navigate(`/${formattedCity}`);
  };

  return (
    <main className="min-h-screen flex flex-col">

      {/* Hero Section (same as Home) */}
          <Hero autoFocus={true} />

      {/* Address Section */}
      {/* <section className="flex flex-col items-center justify-center py-16 bg-gray-50">
        <h2 className="text-3xl font-semibold mb-6">
          Find restaurants near you
        </h2>

        <form
          onSubmit={handleSubmit}
          className="flex gap-4 w-full max-w-xl"
        >
          

          <button
            type="submit"
            className="bg-black text-white px-6 py-3 rounded-xl hover:opacity-90 transition"
          >
            Search
          </button>
        </form>
      </section> */}

      {/* Optional: Show food categories preview */}
      <FoodCategories />

    </main>
  );
}