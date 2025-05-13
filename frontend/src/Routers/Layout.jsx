import React from 'react'
import { Outlet } from 'react-router-dom'
import Navbar from '../component/navbar/Navbar'


export default function RootLayout() {
  return (
      <>
          <Navbar/>
          <main>
            <Outlet/>
          </main>
      </>
  )
}
