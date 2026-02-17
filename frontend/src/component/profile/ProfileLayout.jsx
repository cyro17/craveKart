import { Outlet } from "react-router-dom";
import ProfileSidebar from "./ProfileSidebar";

const ProfileLayout = () => {
    return (
        <div className="max-w-7xl mx-auto px-4 py-6">
            <div className="flex gap-6">
                {/* Sidebar */}
                <aside className="hidden md:block w-64">
                    <ProfileSidebar />
                </aside>

                {/* Main Content */}
                <main className="flex-1 bg-white rounded-xl p-4 shadow-sm">
                    <Outlet />
                </main>
            </div>
        </div>
    );
};

export default ProfileLayout;
