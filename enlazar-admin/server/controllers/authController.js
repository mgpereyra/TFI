const firebase = require( "firebase");
const bcryptj = require('bcryptjs')
const {validationResult}= require('express-validator');
const jwt = require ('jsonwebtoken')

//iniciar sesion
exports.authentication =  async (req, res)=>{
   
    const db = firebase.database().ref();
    const errors = validationResult(req);
    
    if(!errors.isEmpty()){
        return res.status(400).json({errors: errors.array()})
    }

    const {email, password} = req.body;

    try {
        
        const snapshot = await db.child("User").orderByChild("email").equalTo(email).limitToFirst(1).once("value",snapshot => {
            console.log(key)
            return snapshot
        });
        
        //validando existencia de usuario

        if(! snapshot.exists()){
            return res.status(402).json({msg: 'El email ingresado no es válido'})
        }
        var key =  Object.keys( await snapshot.val())[0];
        const data = await snapshot.val()[key]
        
        //revisar el password
        const passCorrect = await bcryptj.compare(password,data.password)
        if(!passCorrect){
            return res.status(400).json({msg: 'La contraseña ingresada es incorrecta'})
        }

        //firebase authentication
        /*firebase.auth().signInWithEmailAndPassword(email, password)
        .then((userCredential) => {
            // Signed in
            var user = userCredential.user;
            // ...
        })
        .catch((error) => {
            var errorCode = error.code;
            var errorMessage = error.message;
        });*/

        //si todo es correcto crear y firmar el token
        const payload = {
            user :{
                id: key
            }
        }

        //firmar el jsonwebtoken
        jwt.sign(payload, process.env.SECRETA, {
        expiresIn: 360000 //1hora              
        },(error, token)=>{
            if(error) throw error;
            res.json({token})
        })
        

    } catch (error) {
        console.log('error')
        console.log(error)
    }
}

//get usuario autenticado
exports.userAuthenticate = async(req, res)=>{
    const db = firebase.database().ref();

    try {
        const user = await db.child("User").child(req.user.id).once("value",snapshot => {
            return snapshot
        });

        let userSinPassword = {}
       
        if(user.exists()){
            userSinPassword = {
                name: user.val().name,
                email: user.val().email,
                id: req.user.id
            }
        }
        res.json({userSinPassword})
    } catch (error) {
        console.log('error')
        console.log(error)
        res.status(500).json({msg:'Hubo un error'})
    }
}