//rutas para autenticar usuario
const express = require('express');
const router = express.Router();
const { check } = require('express-validator')
const authController = require('../controllers/authController')
const auth = require('../middleware/auth')

//para iniciar sesion
//api/auth
router.post('/', 
    authController.authentication
)

//obtiene el usuario autenticado
router.get('/',
    auth,
    authController.userAuthenticate
)
module.exports = router;