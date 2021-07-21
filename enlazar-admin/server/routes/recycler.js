const express = require('express');
const router = express.Router();
const recyclerController = require('../controllers/recyclerController')
const auth = require('../middleware/auth')


//api/recycler
router.get('/',
    auth,
    recyclerController.getListRecycler
)
//api/recycler
router.get('/:id',
    auth,
    recyclerController.getRecycler
)

//api/recycler
router.post('/',
    auth,
    recyclerController.createRecycler
)

//api/recycler
router.put('/:id',
    auth,
    recyclerController.putRecycler
)

//api/recycler
router.delete('/:id',
    auth,
    recyclerController.deleteRecycler
)

module.exports = router