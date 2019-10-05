import React, { Component } from "react"
import { BrowserRouter as Router, Route } from 'react-router-dom'

// import Navbar from './components/Navbar'
// import Dashboard from './components/Dashboard'
// import Login from './components/Login'
import SignUp from './components/SignUp'

class App extends Component {
  render() {
    return (
      <Router>
      <div className="App">
        {/* <Navbar /> */}
        {/* <Route exact path="/dashboard" component={Dashboard} /> */}
        <div className="container">
          <Route exact path="/" component={SignUp} />
          {/* <Route exact path="/login" component={Login} /> */}
        </div>
      </div>
      </Router>
    )
  }
}

export default App
