const express = require('express');
const router = express.Router();
const serviceController = require('../controllers/serviceController')
const auth = require('../middleware/auth')

//api/service
router.get('/',
    auth,
    serviceController.getListServices
)

router.get('/pendings',
    auth,
    serviceController.getListServicesPendings
)

module.exports = router