import React from 'react'

export default function CarouselItem({ image, title}) {
  return (
      <div className='flex flex-col justify-center items-center'>
          <img
              src={image}
              className='w-[10rem] h-[10rem] lg:h-[14rem] lg:w-[14rem] rounded-full object-cover object-center'
              alt=""
          />
          <span className='py-5 font-semibold text-xl text-green-400'>{ title}</span>
    </div>
  )
}
