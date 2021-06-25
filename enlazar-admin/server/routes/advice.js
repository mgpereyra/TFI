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

module.exports = router