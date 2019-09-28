import React, { Component } from "react"
import StyledFirebaseAuth from "react-firebaseui/StyledFirebaseAuth"
import axios from "axios"
import firebase from "firebase"

firebase.initializeApp({
    apiKey: "AIzaSyA8JLEUluEW6leoEAHbFiRT1LFOVs6BsKI",
    authDomain: "kjsce-hacks-2k19.firebaseapp.com",
    databaseURL: "https://kjsce-hacks-2k19.firebaseio.com",
    projectId: "kjsce-hacks-2k19",
    storageBucket: "kjsce-hacks-2k19.appspot.com",
    messagingSenderId: "94913900845",
    appId: "1:94913900845:web:d29d6f30c740312d3de3a4"
})

class App extends Component {
  state = { 
    isSignedIn: false,
    phoneNumber: '+91',
    user: {}
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
        await axios.post("http://localhost:8000/api/update-user-data/", this.state.user, { headers: {"Content-Type": "application/json"} }).then(response => {
        console.log(response.data);
      })}
    }
  }

  // onClickPhone = () => {
  //   var appVerifier = new firebase.auth.RecaptchaVerifier("recaptcha-container",
  //   {
  //      size:"invisible"
  //   });
  //   var provider =firebase.auth.PhoneAuthProvider()
  //   provider.verifyPhoneNumber(this.state.phoneNumber, appVerifier).then((verificationId) => {
  //     var verificationCode = window.prompt('Enter the verification code.')
  //     return firebase.auth.PhoneAuthProvider.credential(verificationId, verificationCode)
  //   }).then((phoneCred) => {
  //     return firebase.auth.signInWithCredentials(phoneCred)
  //   })
  // }

  componentDidMount = () => {
    firebase.auth().onAuthStateChanged(user => {
      this.setState({ isSignedIn: !!user, user: user['providerData'][0] })
      console.log("user", this.state.user)
    })
  }

  // componentDidUpdate = () => {
  //   
  //   }
  // }

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
          </div>
        )}
      </div>
    )
  }
}

export default App
