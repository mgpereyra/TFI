const jwt= require('jsonwebtoken')

module.exports =function(req, res, next){
    //leer el token
    const token = req.header('x-auth-token')
    //revisar
    if(!token){
        res.status(401).json({msg:"No hay token. Permiso no valido"})
    }
    //validar

    try {
        const verificado = jwt.verify(token, process.env.SECRETA)
        req.user = verificado.user;
        next(); 
        
    } catch (error) {
        res.status(401).json({msg:"Token no valido"})
        
    }
}