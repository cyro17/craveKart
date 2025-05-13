import MenuIcon from '@mui/icons-material/Menu';
import { IconButton } from "@mui/material";
import { useContext } from 'react';
import { SideBarContext } from '../State/Store/SideBarContext/SideBarContext';
import { useNavigate } from 'react-router-dom';
import SearchIcon from '@mui/icons-material/Search';

export default function AdminNavbar(){
  const { handleOpenSideBar } = useContext(SideBarContext);
  const navigate = useNavigate();

  return (
    <div className="sticky top-[0] w-full z-50 h-[60px] px-5 py-[.8rem] bg-[#e91e63]  lg:px-20 flex justify-between">
      <div className="flex items-center space-x-4">
        <div className='lg:mr-10 cursor-pointer logo flex items-center'>
          <IconButton onClick={handleOpenSideBar}>
            <MenuIcon sx={{ fontSize: "2rem" }} />
          </IconButton>
          <ul className='logo font-semibold text-gray-200 text-2xl'
            onClick={() => navigate("/")}>
            CraveKart
          </ul>
        </div>
      </div>
    </div>
  );
};
