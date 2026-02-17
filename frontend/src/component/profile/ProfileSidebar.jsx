import { NavLink } from "react-router-dom";

const links = [
    { label: "Profile", to: "/profile" },
    { label: "Orders", to: "/profile/orders" },
    { label: "Favourites", to: "/profile/favourites" },
    { label: "Addresses", to: "/profile/address" },
    { label: "Payments", to: "/profile/payment" },
    { label: "Logout", to: "/profile/logout" },
];

const ProfileSidebar = () => {
    return (
        <nav className="space-y-2">
            {links.map((link) => (
                <NavLink
                    key={link.to}
                    to={link.to}
                    end
                    className={({ isActive }) =>
                        `block px-4 py-2 rounded-lg font-medium ${
                            isActive
                                ? "font-extrabold text-black bg-gray-300 "
                                : "text-gray-700 hover:bg-gray-100"
                        }`
                    }
                >
                    {link.label}
                </NavLink>
            ))}
        </nav>
    );
};

export default ProfileSidebar;
