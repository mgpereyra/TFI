const firebase = require( "firebase");
const {validationResult}= require('express-validator');

exports.createCoupon = async (req, res) =>{
    const {image, title, description, imageCode,amount, pointsCost} = req.body;

    const errors = validationResult(req);
    if(!errors.isEmpty()){
        return res.status(400).json({errors: errors.array()})
    }

    try {
        //creador
        const idUser = req.user.id;

        //guardar proyecto
        const ref = firebase.database().ref('Item').push();
        const id = ref.key

        const advicesave = ref.set({
            image, title, description, imageCode,amount, pointsCost, id
        });
        
        res.send(advicesave)

    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}

//obtener consejos creados por el usuario actual
exports.getListCoupons = async(req, res) => {
    try {
        const db= firebase.database().ref();
        const snapshot = await db.child('Item').once("value",snapshot => {
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
exports.deleteCoupon = async(req, res) => {
    try {
        const db= firebase.database().ref();
        
        //verificar i existe el consejo
        //verificar que el usuario sea el propietario

        const snapshot = await db.child('Item').child(req.params.id).remove()
        res.json({msg: 'consejo eliminado'})
    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}

//Modificar consejo
exports.putCoupon = async(req, res) => {
    try {
        const key = req.params.id;
        const postData = req.body

        const db = firebase.database().ref();
        await db.child('Item').child(key).update(postData)
        
        res.json({msg: 'se modifico correctamente'})
            
    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}

