const AdviceModel = require('../models/adviceModel');
const firebase = require( "firebase");
const {validationResult}= require('express-validator');

exports.createAdvice = async (req, res) =>{
    const {img, tipe, title, description} = req.body;

    //errores
    const errors = validationResult(req);
    if(!errors.isEmpty()){
        return res.status(400).json({errors: errors.array()})
    }

    try {
        //crear consejo
        const advice = new AdviceModel(img, tipe, title, description);
        
        //creador
        const idUser = req.user.id;

        //guardar proyecto
        const advicesave = firebase.database().ref('advice').push({
            img, tipe, title, description, idUser
        }); 

        res.send(advicesave)

    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}

//obtener consejos creados por el usuario actual
exports.getAdvices = async(req, res) => {

    try {
        const db= firebase.database().ref();
        const snapshot = await db.child('advice').orderByChild("idUser").equalTo(req.user.id).once("value",snapshot => {
            return snapshot
        });
        //validando existencia
        if(snapshot.exists()){
            //error
           res.json(snapshot.val())
        }        
    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}