import { NavLink } from "react-router-dom";

const links = [
  { label: "Profile", to: "/my-profile" },
  { label: "Orders", to: "/my-profile/orders" },
  { label: "Favourites", to: "/my-profile/favourites" },
  { label: "Addresses", to: "/my-profile/address" },
  { label: "Payments", to: "/my-profile/payment" },
  { label: "Logout", to: "/my-profile/logout" },
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
                ? "bg-orange-500 text-white"
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
