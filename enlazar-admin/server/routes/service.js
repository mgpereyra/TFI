const express = require('express');
const router = express.Router();
const serviceController = require('../controllers/serviceController')
const auth = require('../middleware/auth')


//api/recycler
router.get('/',
    auth,
    serviceController.getListServices
)

module.exports = router