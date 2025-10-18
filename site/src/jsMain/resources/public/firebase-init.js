import { initializeApp } from "https://www.gstatic.com/firebasejs/10.13.1/firebase-app.js";
import { initializeAppCheck, ReCaptchaV3Provider, getToken } from "https://www.gstatic.com/firebasejs/10.13.1/firebase-app-check.js";

const app = initializeApp({
  apiKey: "AIzaSyCRJtkYXsEuEtIfRtNLoyZr_rpV95TAy-o",
  authDomain: "sorenkai.firebaseapp.com",
  databaseURL: "https://sorenkai-default-rtdb.firebaseio.com",
  projectId: "sorenkai",
  storageBucket: "sorenkai.firebasestorage.app",
  messagingSenderId: "432438073502",
  appId: "1:432438073502:web:773f4b6a8685522acec38f",
  measurementId: "G-W9S93JXZEB"
});

// Pass your reCAPTCHA v3 site key (public key) to activate(). Make sure this
// key is the counterpart to the secret key you set in the Firebase console.
const appCheck = initializeAppCheck(app, {
  provider: new ReCaptchaV3Provider('6LdNL-MrAAAAAGRJFM8dYWdB4oidZUaWOA8dtQ0g'),

  // Optional argument. If true, the SDK automatically refreshes App Check
  // tokens as needed.
  isTokenAutoRefreshEnabled: true
});

// Expose App Check instance and getToken on window for Kotlin access
// (the Kotlin code reads window.appCheck and window.firebase.appCheck.getToken)
window.appCheck = appCheck;
window.firebase = window.firebase || {};
window.firebase.appCheck = { getToken };