const firebase = require( "firebase");
const {validationResult}= require('express-validator');

//obtener recicladors creados por el usuario actual
exports.getListRecycler = async(req, res) => {
    try {
        const db = firebase.database().ref();
        const snapshot = await db.child('User').orderByChild("typeUser").equalTo(2).once("value",snapshot => {
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

exports.createRecycler = async (req, res) =>{
    const {  ubication, dni, email, lat, lng, name, phone,surname  } = req.body;
    //active initDate password typeUser: 2

    const errors = validationResult(req);

    if(!errors.isEmpty()){
        return res.status(400).json({errors: errors.array()})
    }

    try {
        const ref = firebase.database().ref('User').push();
        
        //firebase authentication
        firebase.auth().createUserWithEmailAndPassword(email, pass)
        .then((userCredential) => {
            // Signed in
            var userAuth = userCredential.user;

            firebase.database().ref('User').child(userAuth.uid).set({
                ubication, dni, email, lat, lng, name, phone,surname,
                active: true,  id : userAuth.uid, initDate: Date.now(), 
                password: '123456', typeUser: 2
            });

        })
        .catch((error) => {
            var errorCode = error.code;
            var errorMessage = error.message;
            if (errorCode == 'auth/weak-password') {
                return res.status(500).json({msg: 'La contraseña ingresada no es válida'})
            }
            if (errorCode == 'auth/invalid-email') {
                return res.status(400).json({msg: 'El email ingresado es incorrecto'})
            }
            console.log(errorMessage)
            res.status(500).json({msg: 'Ha ocurrido un error al crear el reciclador'})
        });

    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}

//Modificar meeting
exports.putRecycler = async(req, res) => {
    try {
        const key = req.params.id;
        const postData = req.body

        const db = firebase.database().ref();
        await db.child('User').child(key).update(postData)
        
        res.json({msg: 'se modifico correctamente'})
            
    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}

//Eliminar un reciclador
exports.deleteRecycler = async(req, res) => {
    try {
        const db= firebase.database().ref();
        
        //verificar i existe el reciclador
        //verificar que el usuario sea el propietario

        await db.child('User').child(req.params.id).remove()
        res.json({msg: 'Consejo eliminado'})
    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}
