const firebase = require( "firebase");
const {validationResult}= require('express-validator');

//obtener servicios 
exports.getListServices = async(req, res) => {
    try {
        const db = firebase.database().ref();
        const snapshot = await db.child('Service').once("value",snapshot => {
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
/*
address, 
calculado, 
comentario,
date,
envasesCarton,
envasesPlasticos,
envasesVidrio,
estado,
id,
latitud,
longitud, 
recolectorId,
time
*/
