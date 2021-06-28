const express = require('express');
const router = express.Router();
const meetingController = require('../controllers/meetingController')
const auth = require('../middleware/auth')


//api/meeting
router.get('/',
    auth,
    meetingController.getListMeeting
)

module.exports = router