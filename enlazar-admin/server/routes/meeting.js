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
router.get('/:id',
    auth,
    meetingController.getMeeting
)

//api/meeting
router.post('/',
    auth,
    meetingController.createMeeting
)

//api/meeting
router.put('/:id',
    auth,
    meetingController.putMeeting
)

//api/advice
router.delete('/:id',
    auth,
    meetingController.deleteMeeting
)

module.exports = router