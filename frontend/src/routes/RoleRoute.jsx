
import React from 'react'
import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';

function RoleRoute({ children, role }) {
    const {isAuthenticated, user} = useSelector(state=> state.auth); // Assuming you have an authentication context
    
    if (!isAuthenticated) 
        return <Navigate to="/account/login" replace />;  

    if (user?.role !== role) {
        return <Navigate to="/" replace />;
    }
    return children;
}

export default RoleRoute    