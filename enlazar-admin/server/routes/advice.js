const express = require('express');
const router = express.Router();
const { check } = require('express-validator')
const adviceController = require('../controllers/adviceController')
const auth = require('../middleware/auth')

//api/advice
router.post('/',
    auth,
    adviceController.createAdvice
)

//api/advice
router.get('/',
    auth,
    adviceController.getAdvices
)

//api/coupon
router.get('/:id',
    auth,
    adviceController.getAdvice
)

//api/advice
router.delete('/:id',
    auth,
    adviceController.deleteAdvice
)

//api/advice
router.put('/:id',
    auth,
    adviceController.putAdvice
)
module.exports = router