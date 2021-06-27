import firebase from 'firebase/app';

const config =  {
    apiKey: "AIzaSyD63Ptld6R6m6o8p3HkX9an3ksAw_faFBs",
    authDomain: "enlazar-admin.firebaseapp.com",
    databaseURL: "https://enlazar-admin-default-rtdb.firebaseio.com",
    projectId: "enlazar-admin",
    storageBucket: "enlazar-admin.appspot.com",
    messagingSenderId: "668817016068",
    appId: "1:668817016068:web:3d9d529b4d2461b350bc51",
    measurementId: "G-PK4PTWB3DW",
  };

export function conectFirebase (){
    try{
        // Initialize Firebase
          firebase.initializeApp(config)
     }catch(error){
          console.log('error')
          console.log(error)  
     }
}

