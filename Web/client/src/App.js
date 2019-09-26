import React, { Component } from "react"
import "./App.css"
import firebase from "firebase"
import StyledFirebaseAuth from "react-firebaseui/StyledFirebaseAuth"

firebase.initializeApp({
  apiKey: "AIzaSyDUP81oZRkZJS_TRYDnXelp9_TWNCj2OWA",
  authDomain: "kjscehack-fa5ae.firebaseapp.com",
  databaseURL: "https://kjscehack-fa5ae.firebaseio.com",
  projectId: "kjscehack-fa5ae",
  storageBucket: "",
  messagingSenderId: "803546715173",
  appId: "1:803546715173:web:ec618fcdabd348a278dbcc",
  measurementId: "G-H4SLLNZ59N"
})

class App extends Component {
  state = { isSignedIn: false }
  uiConfig = {
    signInFlow: "popup",
    signInOptions: [
      firebase.auth.GoogleAuthProvider.PROVIDER_ID,
      firebase.auth.FacebookAuthProvider.PROVIDER_ID,
      firebase.auth.TwitterAuthProvider.PROVIDER_ID,
      firebase.auth.GithubAuthProvider.PROVIDER_ID,
      firebase.auth.EmailAuthProvider.PROVIDER_ID
    ],
    callbacks: {
      signInSuccess: () => false
    }
  }

  onClickPhone() {
    const phoneNumber = this.phone;
    const appVerifier = window.recaptchaVerifier;
    firebase
    .auth()
    .signInWithPhoneNumber(phoneNumber, appVerifier)
    .then(confirmResult => {
      // success
    })
    .catch(error => {
      // error
    });
  }

  componentDidMount = () => {
    firebase.auth().onAuthStateChanged(user => {
      this.setState({ isSignedIn: !!user })
      console.log("user", user)
      console.log(user.getIdToken())
    })
    window.recaptchaVerifier = new firebase.auth.RecaptchaVerifier("recaptcha-container",
    {
       size:"invisible"
        // other options
    });
  }

  render() {
    return (
      <div className="App">
        {this.state.isSignedIn ? (
          <span>
            <div>Signed In!</div>
            <button onClick={() => firebase.auth().signOut()}>Sign out!</button>
            <h1>Welcome {firebase.auth().currentUser.displayName} </h1>
            <img
              alt="profile picture"
              src={firebase.auth().currentUser.photoURL}
            />
          </span>
        ) : (
          <div>
            <StyledFirebaseAuth
              uiConfig={this.uiConfig}
              firebaseAuth={firebase.auth()}
            />
            <input id="recaptcha-container" type="button" onClick={this.onClickPhone} />
          </div>
        )}
      </div>
    )
  }
}

export default App
