import { useState } from "react";
import { SideBarContext } from "./SideBarContext";

export default function SideBarProvider({ children }) {
    const [open, setOpenSideBar] = useState(false);
    const handleOpenSideBar = () => setOpenSideBar(true);
    const handleCloseSideBar = () => setOpenSideBar(false);
    return (
        <SideBarContext.Provider value={{ open, handleCloseSideBar, handleOpenSideBar }}>
            {children }
        </SideBarContext.Provider>)
};