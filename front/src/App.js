import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Login from './Login';
import SignUp from './SignUp';
import Home from './pages/main/Home';
import InterestPlace from './pages/customer/InterestPlace';
import Header from './pages/main/Header';
import AppRouter from './components/AppRouter';

function App() {

  return (
    <Router>
      <Header />
      <div>        
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/InterestPlace" element={<InterestPlace />}/>
        </Routes>
      </div>
    </Router>
  );
}



export default App;
