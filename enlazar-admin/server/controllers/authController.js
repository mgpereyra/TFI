const firebase = require( "firebase");
const bcryptj = require('bcryptjs')
const {validationResult}= require('express-validator');
const jwt = require ('jsonwebtoken')

exports.authentication =  async (req, res)=>{
   
    const db = firebase.database().ref();
    const errors = validationResult(req);
    
    if(!errors.isEmpty()){
        return res.status(400).json({errors: errors.array()})
    }

    const {email, password} = req.body;

    try {
        
        const snapshot = await db.child("User").orderByChild("email").equalTo(email).limitToFirst(1).once("value",snapshot => {
            return snapshot
        });

        //validando existencia
        if(! snapshot.exists()){
            return res.status(400).json({msg: 'El usuario no existe'})
        }else{
            const data = await snapshot.val()
            let user;
            let id;

            for(const prop in data){
                id = prop
                user = data[prop]
            }

            const passCorrect = await bcryptj.compare(password,user.password)
            if(!passCorrect){
                return res.status(400).json({msg: 'El password es incorrecto'})
            }

            //crear y firmar el token
            const payload = {
                user :{
                    id: id
                }
            }

            //firmar el jsonwebtoken
            jwt.sign(payload, process.env.SECRETA, {
            expiresIn: 3600 //1hora              
            },(error, token)=>{
                if(error) throw error;
                res.json({token})
            })


        }

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