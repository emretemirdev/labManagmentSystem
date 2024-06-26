// App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from '../pages/signIn/signIn';
import Dashboard from '../pages/dashboard/Dashboard';
import PrivateRoute from '../utils/PrivateRoute'; 

function App() {
  return (
      <Router>
        
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
          <Route path="/" element={<Navigate replace to="/login" />} />
        </Routes>
      </Router>
  );
}

export default App;