const express = require('express');
const router = express.Router();
const userController = require('../controllers/userController')
const { check } = require('express-validator')
const auth = require('../middleware/auth')

//para crear usuarios
//api/user
router.post('/', 
[
    check('name', 'El nombre es obligatorio').not().isEmpty(),
    check('email', 'Agrega un email válido').isEmail(),
    check('password', 'La contraseña debe tener un mínimo de 6 caracteres').isLength({min:6}),

],
    userController.createUser,
    auth
)

module.exports = router;