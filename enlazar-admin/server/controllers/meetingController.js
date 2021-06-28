const firebase = require( "firebase");
const {validationResult}= require('express-validator');

//obtener consejos creados por el usuario actual
exports.getListMeeting = async(req, res) => {
    try {
        const db= firebase.database().ref();
        const snapshot = await db.child('MeetingPoint').orderByChild("date").once("value",snapshot => {
            return snapshot
        });
     
        //validando existencia
        if(snapshot.exists()){
           res.json(snapshot.val())
        }        
    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}