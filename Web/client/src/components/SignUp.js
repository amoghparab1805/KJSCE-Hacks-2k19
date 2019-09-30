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
      {
        provider: firebase.auth.PhoneAuthProvider.PROVIDER_ID,
        recaptchaParameters: {
          type: 'image',
          size: 'normal',
          badge: 'bottomleft'
        },
        defaultCountry: 'IN'
      }
    ],
    callbacks: {
      signInSuccessWithAuthResult: async (authResult) => {
        await axios.post("http://localhost:8000/api/sign-up/", 
          JSON.stringify(authResult.user['providerData'][0]), 
          { headers: {"Content-Type": "application/json"} }
        ).then(response => {
          console.log(response.data)
          localStorage.setItem("token", response.data.token)
        })
      },
      // onVerificationCompleted: async (phoneCredentials) => {
      //   console.log("Hahaha")
      //   console.log(phoneCredentials)
      //   axios.post("http://localhost:8000/api/sign-up/", 
      //     JSON.stringify(authResult.user['providerData'][0]), 
      //     { headers: {"Content-Type": "application/json"} }
      //   ).then(response => {
      //     console.log(response.data)
      //     localStorage.setItem("token", response.data.token)
      //   })
      // }
    }
  }

  signOut = async() => {
    await axios.post("http://localhost:8000/api/auth/token/logout/", {},
    { headers: {"Authorization" : `Token ${localStorage.getItem("token")}`} }).then(response => {
      console.log("Successfully Logged Out")
      localStorage.removeItem("token")
    })
    localStorage.removeItem('token')
    firebase.auth().signOut()
  }

  componentDidMount = () => {
    firebase.auth().onAuthStateChanged(user => {
      this.setState({ isSignedIn: !!user })
      console.log("user", user)
      if(!!user){
        if(user['providerData'][0]['providerId'] === 'phone'){
          // open modal here
          
          // var data = JSON.stringify(user['providerData'][0])
          // data['displayName'] = displayName
          // request
          axios.post("http://localhost:8000/api/sign-up/",{ 
            displayName: "Harsh Mistry",
            email: null,
            phoneNumber: user['providerData'][0]['phoneNumber'],
            photoURL: null,
            providerId: "phone",
            uid: user['providerData'][0]['uid']
          }, 
            { headers: {"Content-Type": "application/json"} }
          ).then(response => {
            console.log(response.data)
            localStorage.setItem("token", response.data.token)
          })
        }
      }
    })
  }

  render() {
    return (
    <div className="App">
        {this.state.isSignedIn ? (
          <span>
            <div>Signed In!</div>
            <button onClick={this.signOut}>Sign out!</button>
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
          </div>
        )}
      </div>
      )
    }
  }
  
export default SignUp  