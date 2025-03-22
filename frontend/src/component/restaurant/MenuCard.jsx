import React from 'react';

import { Accordion, Button, Checkbox, FormControlLabel, FormGroup } from '@mui/material';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';


const demo = [
    {  
        category: "nuts & seeds", 
        ingredient: ["Cashews"], 
    }, 
    {
        category: "Protein",
        ingredient: ["Bacon chips", "chicken"],
    }, 
];


export default function MenuCard({ label }) {
    
    function handleCheckboxChange(item) {
        console.log(item)
    }
    
  return (
      <div>
          <Accordion>
            <AccordionSummary
            expandIcon={<ExpandMoreIcon />}
            aria-controls="panel1-content"
            id="panel1-header"
            >
                  <div className='lg:flex items-center justify-between'>
                      <div className='lg:flex items-center lg:gap-5' >
                          <img className='w-[7rem] h-[7rem] object-cover'
                              src="https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=1899&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" alt="" />
                      
                            <div className='space-y-1 space-x-5 lg:space-y-5 lg:max-w-2xl'>
                                <p >Burger</p>
                                <p>Rs 499</p>
                                <p className='text-gray-400'>nice food</p>
                            </div>
                        </div>
                  </div>
            </AccordionSummary>
            <AccordionDetails>
                <form action="">
                      <div className='flex gap-5 flex-wrap'>
                          {
                              demo.map((item) =>
                                  <div>
                                      <p>{item.category}</p>
                                      <FormGroup>
                                          {item.ingredient.map((ingredient) =>
                                              <FormControlLabel control={<Checkbox defaultChecked onChange={()=>handleCheckboxChange(item)}/>} label={ingredient} />
                                        )}
                                      </FormGroup>
                                  </div>
                              )
                          }
                      </div>
                      <div className='pt-5'>
                          <Button type='submit' disabled={ false}>
                              {true ? "Add To Cart" : "out of stock"}</Button>
                      </div>
                </form>
            </AccordionDetails>
          </Accordion>
    </div>
  )
}
