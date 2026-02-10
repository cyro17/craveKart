import React from 'react'

export default function
  CarouselItem({ image, title }) {
  return (
    <div className='px-2'>
      <div className='flex flex-col gap-2 justify-center items-center cursor-pointer group'>
        <div className='h-15 w-15 '>

            <img
                src={image}
                className='w-[10rem] h-[10rem] lg:h-[14rem] lg:w-[14rem] rounded-full object-cover object-center'
                alt=""
                />
        </div>
            <span className='py-5 font-semibold text-xl text-green-400'>{ title}</span>
      </div>
    </div>
  )
}
