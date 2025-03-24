import React, { useState } from 'react'
import ProfileNavigation from './ProfileNavigation'

export default function Profile() {
    const [openSideBar, setOpenSideBar] = useState(false);
    return (
      <div className='lg: flex justify-between'>
          <div className='sticky h-[80vh] lg:w-[20%]' >
             <ProfileNavigation open={openSideBar} />
          </div>

          <div className='lg:2-[80%]'>
              
          </div>
        
    </div>
  )
}
