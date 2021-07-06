const firebase = require( "firebase");
const bcryptj = require('bcryptjs')
const {validationResult}= require('express-validator');
const jwt = require ('jsonwebtoken')

exports.createUser =  async (req, res) => {
    const db= firebase.database().ref();
    const {name, email, password} = req.body;
    const errors = validationResult(req);
    
    if(!errors.isEmpty()){
        return res.status(400).json({errors: errors.array()})
    }

    try {
        
        const users=  await db.child("User");
        
        const exist = await users.orderByChild("email").equalTo(email).limitToFirst(1).once("value",snapshot => {
            return snapshot
        });
        //validando existencia
        if(exist.exists()){
            //error
            return res.status(400).json({msg: 'Ya existe un usuario registrado con el email ingresado'})
        }

        //hash de password
        const salt = await bcryptj.genSalt(10);
        let pass = await bcryptj.hash(password, salt);

        //firebase authentication

        firebase.auth().createUserWithEmailAndPassword(email, pass)
        .then((userCredential) => {
            // Signed in
            var userAuth = userCredential.user;

            firebase.database().ref('User').child(userAuth.uid).set({
                name: name,
                email: email,
                password: pass,
                typeUser: 0,
                id:userAuth.uid
            });

            //crear y firmar el token
            const payload = {
                user : {
                    id: userAuth.uid
                }
            }

            //firmar el jsonwebtoken
            jwt.sign(payload, process.env.SECRETA, {
            expiresIn: 3600 //1hora              
            },(error, token)=>{
                if(error) throw error;
                res.json({token})
            })
                // ...
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
            res.status(500).json({msg: 'Ha ocurrido un error'})
        });
        
        //mensaje de confirmacion

    } catch (error) {
        console.log(error)
        res.status(400).send('hubo un error')
    }
}