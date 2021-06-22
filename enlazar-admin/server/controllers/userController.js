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
            return res.status(400).json({msg: 'El usuario ya existe'})
        }

        //hash de password
        const salt = await bcryptj.genSalt(10);
        let pass = await bcryptj.hash(password, salt);
            
        //guardar Usuario
        const userSaveId = firebase.database().ref('User').push({
            name: name,
            email: email,
            password: pass
        }).getKey();

        const user = {
            id: userSaveId
        }
        //crear y firmar el token
        const payload = {
            user : {
                id: user.id
            }
        }

        //firmar el jsonwebtoken
        jwt.sign(payload, process.env.SECRETA, {
         expiresIn: 3600 //1hora              
        },(error, token)=>{
            if(error) throw error;
            res.json({token})
        })

        //mensaje de confirmacion

    } catch (error) {
        console.log(error)
        res.status(400).send('hubo un error')
    }
}