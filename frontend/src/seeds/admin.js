
export const orders = [
    {
        id: "#CK-8821", customer: "Ananya Roy", customerPhone: "+91 98100 11111", customerEmail: "ananya@gmail.com", restaurant: "Biryani Blues", restaurantCity: "Delhi",
        items: [{ name: "Chicken Biryani", qty: 2, price: 280 }, { name: "Raita", qty: 1, price: 60 }], amount: 640, status: "delivered", time: "2m ago", date: "Mar 4, 2026", city: "Delhi", address: "42, Lajpat Nagar, Delhi"
    },
    { id: "#CK-8820", customer: "Rishi Kapoor", customerPhone: "+91 97200 22222", customerEmail: "rishi@gmail.com", restaurant: "Pizza Palace", restaurantCity: "Bangalore", items: [{ name: "Margherita Pizza", qty: 1, price: 320 }, { name: "Coke", qty: 1, price: 60 }], amount: 420, status: "in-transit", time: "8m ago", date: "Mar 4, 2026", city: "Bangalore", address: "15, Koramangala, Bangalore" },
    { id: "#CK-8819", customer: "Meera Nair", customerPhone: "+91 96300 33333", customerEmail: "meera@gmail.com", restaurant: "Burger Barn", restaurantCity: "Mumbai", items: [{ name: "Classic Burger", qty: 1, price: 199 }], amount: 199, status: "preparing", time: "12m ago", date: "Mar 4, 2026", city: "Mumbai", address: "8, Bandra West, Mumbai" },
    { id: "#CK-8818", customer: "Arjun Dev", customerPhone: "+91 95400 44444", customerEmail: "arjun@gmail.com", restaurant: "Tandoor Tales", restaurantCity: "Pune", items: [{ name: "Dal Makhani", qty: 2, price: 240 }, { name: "Butter Naan", qty: 4, price: 160 }, { name: "Lassi", qty: 2, price: 120 }], amount: 890, status: "delivered", time: "18m ago", date: "Mar 4, 2026", city: "Pune", address: "23, Koregaon Park, Pune" },
    { id: "#CK-8817", customer: "Kavya Reddy", customerPhone: "+91 94500 55555", customerEmail: "kavya@gmail.com", restaurant: "Wok This Way", restaurantCity: "Hyderabad", items: [{ name: "Hakka Noodles", qty: 1, price: 220 }, { name: "Spring Rolls", qty: 2, price: 130 }], amount: 350, status: "cancelled", time: "25m ago", date: "Mar 4, 2026", city: "Hyderabad", address: "11, Banjara Hills, Hyderabad" },
    { id: "#CK-8816", customer: "Saurabh Jain", customerPhone: "+91 93600 66666", customerEmail: "saurabh@gmail.com", restaurant: "Biryani Blues", restaurantCity: "Delhi", items: [{ name: "Mutton Biryani", qty: 3, price: 960 }, { name: "Shorba", qty: 1, price: 80 }], amount: 1200, status: "delivered", time: "31m ago", date: "Mar 4, 2026", city: "Delhi", address: "7, Saket, Delhi" },
    { id: "#CK-8815", customer: "Divya Iyer", customerPhone: "+91 92700 77777", customerEmail: "divya@gmail.com", restaurant: "Pizza Palace", restaurantCity: "Bangalore", items: [{ name: "Farmhouse Pizza", qty: 1, price: 299 }], amount: 299, status: "preparing", time: "45m ago", date: "Mar 4, 2026", city: "Bangalore", address: "3, Indiranagar, Bangalore" },
    { id: "#CK-8814", customer: "Rohan Mehta", customerPhone: "+91 91800 88888", customerEmail: "rohan@gmail.com", restaurant: "Dosa Delight", restaurantCity: "Bangalore", items: [{ name: "Masala Dosa", qty: 2, price: 180 }, { name: "Filter Coffee", qty: 2, price: 80 }], amount: 440, status: "delivered", time: "1h ago", date: "Mar 4, 2026", city: "Bangalore", address: "19, Jayanagar, Bangalore" },
    { id: "#CK-8813", customer: "Pooja Sharma", customerPhone: "+91 90900 99999", customerEmail: "pooja@gmail.com", restaurant: "Rolls & Wraps", restaurantCity: "Delhi", items: [{ name: "Paneer Roll", qty: 3, price: 270 }, { name: "Fries", qty: 1, price: 80 }], amount: 380, status: "cancelled", time: "1h ago", date: "Mar 4, 2026", city: "Delhi", address: "55, Rajouri Garden, Delhi" },
    { id: "#CK-8812", customer: "Vikram Singh", customerPhone: "+91 89000 00000", customerEmail: "vikram@gmail.com", restaurant: "Tandoor Tales", restaurantCity: "Pune", items: [{ name: "Paneer Tikka", qty: 2, price: 380 }, { name: "Roti", qty: 4, price: 80 }], amount: 580, status: "in-transit", time: "1h ago", date: "Mar 4, 2026", city: "Pune", address: "31, Viman Nagar, Pune" },
];

export const users = [
    { id: 1, name: "Ananya Roy", email: "ananya@gmail.com", phone: "+91 98100 11111", role: "CUSTOMER", city: "Delhi", orders: 28, spent: 18400, joined: "Jan 2024", status: "active", lastActive: "2m ago" },
    { id: 2, name: "Rahul Sharma", email: "rahul@biryaniblues.com", phone: "+91 97200 22222", role: "RESTAURANT", city: "Delhi", orders: 892, spent: 284000, joined: "Jan 2024", status: "active", lastActive: "5m ago" },
    { id: 3, name: "Rishi Kapoor", email: "rishi@gmail.com", phone: "+91 96300 33333", role: "CUSTOMER", city: "Bangalore", orders: 14, spent: 8200, joined: "Feb 2024", status: "active", lastActive: "1h ago" },
    { id: 4, name: "Meera Nair", email: "meera@gmail.com", phone: "+91 95400 44444", role: "CUSTOMER", city: "Mumbai", orders: 7, spent: 3100, joined: "Mar 2024", status: "active", lastActive: "3h ago" },
    { id: 5, name: "Arjun Dev", email: "arjun@gmail.com", phone: "+91 94500 55555", role: "CUSTOMER", city: "Pune", orders: 52, spent: 41200, joined: "Nov 2023", status: "banned", lastActive: "2d ago" },
    { id: 6, name: "Priya Singh", email: "priya@burgerplace.com", phone: "+91 93600 66666", role: "RESTAURANT", city: "Mumbai", orders: 0, spent: 0, joined: "Mar 2024", status: "active", lastActive: "1d ago" },
    { id: 7, name: "Kavya Reddy", email: "kavya@gmail.com", phone: "+91 92700 77777", role: "CUSTOMER", city: "Hyderabad", orders: 33, spent: 22800, joined: "Dec 2023", status: "active", lastActive: "30m ago" },
    { id: 8, name: "Saurabh Jain", email: "saurabh@gmail.com", phone: "+91 91800 88888", role: "CUSTOMER", city: "Delhi", orders: 61, spent: 54300, joined: "Oct 2023", status: "active", lastActive: "15m ago" },
    { id: 9, name: "Vikram Rao", email: "vikram@curryhouse.com", phone: "+91 90900 99999", role: "RESTAURANT", city: "Chennai", orders: 0, spent: 0, joined: "Mar 2024", status: "active", lastActive: "6h ago" },
    { id: 10, name: "Divya Iyer", email: "divya@gmail.com", phone: "+91 89000 10101", role: "CUSTOMER", city: "Bangalore", orders: 19, spent: 12600, joined: "Feb 2024", status: "banned", lastActive: "5d ago" },
    { id: 11, name: "Rohan Mehta", email: "rohan@gmail.com", phone: "+91 88100 11212", role: "CUSTOMER", city: "Bangalore", orders: 44, spent: 31700, joined: "Sep 2023", status: "active", lastActive: "45m ago" },
    { id: 12, name: "Sunita Mehta", email: "sunita@tandoortales.com", phone: "+91 87200 12323", role: "RESTAURANT", city: "Pune", orders: 445, spent: 142000, joined: "Nov 2023", status: "active", lastActive: "20m ago" },
];


export const fmt = (n) => `₹${n.toLocaleString("en-IN")}`;
export const getInitials = (name) => name?.split(" ").map(n => n[0]).join("").toUpperCase();
