
        var firebaseConfig = {
            apiKey: "AIzaSyD63Ptld6R6m6o8p3HkX9an3ksAw_faFBs",
        authDomain: "enlazar-admin.firebaseapp.com",
        databaseURL: "https://enlazar-admin-default-rtdb.firebaseio.com",
        projectId: "enlazar-admin",
        storageBucket: "enlazar-admin.appspot.com",
        messagingSenderId: "668817016068",
        appId: "1:668817016068:web:8f7aec1e338b9a7e50bc51",
        measurementId: "G-CEFFVVL3ZR"
      };
      // Initialize Firebase
      firebase.initializeApp(firebaseConfig);
      firebase.analytics();

const dbRef = firebase.database().ref();
dbRef.child("user").child(Id).get().then((snapshot) => {
    if (snapshot.exists()) {
        console.log(snapshot.val());
    } else {
        console.log("No data available");
    }
}).catch((error) => {
    console.error(error);
});
