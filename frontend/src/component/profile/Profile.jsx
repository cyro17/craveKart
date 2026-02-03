import React, { useState } from 'react'
import ProfileNavigation from './ProfileNavigation'
import { Outlet  } from 'react-router-dom';


export default function Profile() {
    const [openSideBar, setOpenSideBar] = useState(false);
    return (
        
        <div className="lg:flex lg:space-x-4 p-4">
            {/* Sidebar */}
            <div className="sticky top-0 h-[80vh] lg:w-[20%]">
                <ProfileNavigation
                    open={openSideBar}
                    handleClose={() => setOpenSideBar(false)} />
            </div>
    
            {/* Main Content */}
            User profile
            <div className="flex-1">
                <Outlet /> {/* Renders orders, favourites, address, etc. */}
            </div>
      </div>
  )
}
