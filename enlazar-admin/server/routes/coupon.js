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
router.get('/:idUser/:idCoupon',
    auth,
    couponController.verifyCoupon
)


//api/coupon
router.get('/:id',
    auth,
    couponController.getCoupon
)

//api/coupon
router.get('/:idUser/:idCoupon/:idItem',
    auth,
    couponController.verifyCouponCamera
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
router.put('/confirm/:id',
    auth,
    couponController.confirmCanjeCoupon
)


//api/coupon
router.delete('/:id',
    auth,
    couponController.deleteCoupon
)

module.exports = router