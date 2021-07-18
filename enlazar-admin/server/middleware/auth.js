const jwt= require('jsonwebtoken')

module.exports =function(req, res, next){
    //leer el token
    const token = req.header('x-auth-token')
    //revisar
    if(!token){
        res.status(401).json({msg:"Por favor, recuerde iniciar sesión"})
    }

    //validar
    try {
        const verificado = jwt.verify(token, process.env.SECRETA)
        //guardo lo datos del usuario en request
        req.user = verificado.user;
        next(); 
        
    } catch (error) {
        //redireccion cuando se termina la sesion
        console.log(error)
        res.status(401).json({msg:"Recuerda iniciar sesión"})
    }
}