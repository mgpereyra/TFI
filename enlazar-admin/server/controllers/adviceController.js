const AdviceModel = require('../models/adviceModel');
const firebase = require( "firebase");
const {validationResult}= require('express-validator');
const {Storage} = require('@google-cloud/storage')
const storage = require( 'firebase/storage');
global.XMLHttpRequest = require("xhr2");

exports.createAdvice = async (req, res) =>{
    const {img, tipe, title, content, uri} = req.body;
    const errors = validationResult(req);
    if(!errors.isEmpty()){
        return res.status(400).json({errors: errors.array()})
    }

    try {
        //creador
        const idUser = req.user.id;
        let likes = 0

        //guardar proyecto
        const ref = firebase.database().ref('Advice').push();
        const id = ref.key

        const advicesave = ref.set({
            img, tipe, title, content, idUser, likes, id, uri
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
        const snapshot = await db.child('Advice').orderByChild("idUser").equalTo(req.user.id).once("value",snapshot => {
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


//Eliminar un consejo
exports.deleteAdvice = async(req, res) => {
    try {
        const db= firebase.database().ref();
        
        //verificar i existe el consejo
        //verificar que el usuario sea el propietario

        const snapshot = await db.child('Advice').child(req.params.id).remove()
        res.json({msg: 'proyecto eliminado'})
    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}

//Modificar consejo
exports.putAdvice = async(req, res) => {
    try {
        const key = req.params.id;
        const postData = req.body

        const db = firebase.database().ref();
        await db.child('Advice').child(key).update(postData)
        
        res.json({msg: 'se modifico correctamente'})
            
    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}

