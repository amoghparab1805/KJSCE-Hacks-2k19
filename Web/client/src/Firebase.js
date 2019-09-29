import firebase from "firebase"

const config = {
    apiKey: "AIzaSyA8JLEUluEW6leoEAHbFiRT1LFOVs6BsKI",
    authDomain: "kjsce-hacks-2k19.firebaseapp.com",
    databaseURL: "https://kjsce-hacks-2k19.firebaseio.com",
    projectId: "kjsce-hacks-2k19",
    storageBucket: "kjsce-hacks-2k19.appspot.com",
    messagingSenderId: "94913900845",
    appId: "1:94913900845:web:d29d6f30c740312d3de3a4"
};
firebase.initializeApp(config);
export default firebase;