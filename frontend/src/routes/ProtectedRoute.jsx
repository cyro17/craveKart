

import React from 'react'
import { Navigate, useLocation } from 'react-router-dom';

function ProtectedRoute({ children }) {
    const { isAuthenticated } = false;
    const location = useLocation();
    
    if (!isAuthenticated) {
        return (
            <Navigate
                to="/account/login"
                replace
                state={{ from: location.pathname }}
            /> 
        )
    }
  return children;
}

export default ProtectedRoute