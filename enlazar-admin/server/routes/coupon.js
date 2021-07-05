const express = require('express');
const router = express.Router();
const couponController = require('../controllers/couponController')
const auth = require('../middleware/auth')


//api/coupon
router.get('/',
    auth,
    couponController.getListCoupons
)

//api/coupon
router.post('/',
    auth,
    couponController.createCoupon
)

//api/coupon
router.put('/:id',
    auth,
    couponController.putCoupon
)

//api/coupon
router.delete('/:id',
    auth,
    couponController.deleteCoupon
)

module.exports = router