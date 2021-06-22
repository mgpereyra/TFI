const express = require('express');
const router = express.Router();
const { check } = require('express-validator')
const adviceController = require('../controllers/adviceController')
const auth = require('../middleware/auth')

//api/advice
router.post('/',
    auth,
    [
        check('img', "La imagen del consejo es obligatoria").not().isEmpty(),
        check('title', "El título del consejo es obligatorio").not().isEmpty(),
        check('description', "La descripción del consejo es obligatoria").not().isEmpty(),
        check('tipe', "El tipo de consejo es obligatorio").not().isEmpty(),

    ],
    adviceController.createAdvice
)

//api/advice
router.get('/',
    auth,
    adviceController.getAdvices
)

module.exports = router