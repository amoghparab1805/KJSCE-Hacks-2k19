import React, { Component } from "react"
import StyledFirebaseAuth from "react-firebaseui/StyledFirebaseAuth"
import axios from "axios"
import firebase from "../Firebase"

class SignUp extends Component {
  state = { 
    isSignedIn: false,
    phoneUserDetails: {},
    emailUser: {},
  }
  uiConfig = {
    signInFlow: "popup",
    signInOptions: [
      firebase.auth.GoogleAuthProvider.PROVIDER_ID,
      // firebase.auth.FacebookAuthProvider.PROVIDER_ID,
      firebase.auth.PhoneAuthProvider.PROVIDER_ID,
    ],
    callbacks: {
      signInSuccessWithAuthResult: async () => {
        if (firebase.auth().currentUser['providerData'][0].providerId === 'google.com') {
          await axios.post("http://localhost:8000/api/google-sign-up/", 
            JSON.stringify(firebase.auth().currentUser['providerData'][0]), 
            { headers: {"Content-Type": "application/json"} }
          ).then(response => {
            console.log(response.data)
          }) 
        }
        // if (firebase.auth().currentUser['providerData'][0].providerId === 'phone') {
        //   await axios.post("http://localhost:8000/api/phone-sign-up/", 
        //     JSON.stringify(this.state.phoneUserDetails), 
        //     { headers: {"Content-Type": "application/json"} }
        //   ).then(response => {
        //     console.log(response.data)
        //   }) 
        // }
      }
    }
  }

  // onClickSignUp = async() => {
  //   await axios.post("http://localhost:8000/api/email-sign-up/", 
  //     JSON.stringify(this.state.emailUser), 
  //     { headers: {"Content-Type": "application/json"} }
  //   ).then(response => {
  //     console.log(response.data)
  //   })
  // }

  componentDidMount = () => {
    firebase.auth().onAuthStateChanged(user => {
      this.setState({ isSignedIn: !!user })
      console.log("user", user)
    })
  }

  render() {
    return (
    <div className="App">
        {this.state.isSignedIn ? (
          <span>
            <div>Signed In!</div>
            <button onClick={() => firebase.auth().signOut()}>Sign out!</button>
            <h1>Welcome {firebase.auth().currentUser.displayName} {firebase.auth().currentUser.phoneNumber}</h1>
            <img
              alt="profile"
              src={firebase.auth().currentUser.photoURL}
            />
          </span>
        ) : (
          <div>
            <StyledFirebaseAuth
              uiConfig={this.uiConfig}
              firebaseAuth={firebase.auth()}
            />
            <hr width="300px" style={{borderTop:"3px dashed"}}/>
            <div style={{textAlign: "center"}}>
              <input type="text" name="displayName" placeholder="Full Name"/><br/>
              <input type="email" name="email" placeholder="Email"/><br/>
              <input type="password" name="password" placeholder="Password"/><br/>
              <input type="submit" name="submit" onClick={this.onClickSignUp}/>
            </div>
          </div>
        )}
      </div>
      )
    }
  }
  
export default SignUp  