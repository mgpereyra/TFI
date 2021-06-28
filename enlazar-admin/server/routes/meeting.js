const express = require('express');
const router = express.Router();
const meetingController = require('../controllers/meetingController')
const auth = require('../middleware/auth')


//api/meeting
router.get('/',
    auth,
    meetingController.getListMeeting
)

//api/meeting
router.post('/',
    auth,
    meetingController.createMeeting
)

module.exports = router