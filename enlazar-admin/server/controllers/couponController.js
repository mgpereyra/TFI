const firebase = require("firebase");
const { validationResult } = require("express-validator");

exports.createCoupon = async (req, res) => {
  const { image, title, description, imageCode, amount, pointsCost } = req.body;

  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({ errors: errors.array() });
  }

  try {
    //creador
    const idUser = req.user.id;

    //guardar proyecto
    const ref = firebase.database().ref("Item").push();
    const id = ref.key;

    const advicesave = ref.set({
      image,
      title,
      description,
      imageCode,
      amount: parseInt(amount, 10),
      pointsCost: parseInt(pointsCost, 10),
      id,
    });

    res.send(advicesave);
  } catch (error) {
    console.log(error);
    res.status(500).send("Error");
  }
};

//obtener consejos creados por el usuario actual
exports.getListCoupons = async (req, res) => {
  try {
    const db = firebase.database().ref();
    const snapshot = await db.child("Item").once("value", (snapshot) => {
      return snapshot;
    });
    //validando existencia
    if (snapshot.exists()) {
      //error
      res.json(snapshot.val());
    }
  } catch (error) {
    console.log(error);
    res.status(500).send("Error");
  }
};

//Eliminar un consejo
exports.deleteCoupon = async (req, res) => {
  try {
    const db = firebase.database().ref();

    //verificar i existe el consejo
    //verificar que el usuario sea el propietario

    const snapshot = await db.child("Item").child(req.params.id).remove();
    res.json({ msg: "consejo eliminado" });
  } catch (error) {
    console.log(error);
    res.status(500).send("Error");
  }
};

//Modificar consejo
exports.putCoupon = async (req, res) => {
  try {
    const key = req.params.id;
    const postData = req.body;
    postData.pointsCost = parseInt(postData.pointsCost, 10)
    postData.amount = parseInt(postData.amount, 10)

    const db = firebase.database().ref();
    await db.child("Item").child(key).update(postData);

    res.json({ msg: "se modifico correctamente" });
  } catch (error) {
    console.log(error);
    res.status(500).send("Error");
  }
};

exports.putQrCoupon = async (req, res) => {
  try {
    const key = req.params.id;
    const postData = req.body;

    console.log(key)
    console.log(postData)

    const db = firebase.database().ref();
    await db.child("Item").child(key).update({ "imageCode" : postData.urlCode});

    res.json({ msg: "se modifico correctamente" });
  } catch (error) {
    console.log(error);
    res.status(500).send("Error");
  }
};


//obtener consejos creados por el usuario actual
exports.verifyCoupon = async (req, res) => {
  try {
    const db = firebase.database().ref();

    const keyCoupon = req.params.idCoupon;
    const keyUser = req.params.idUser;

    //trae el usuario
    const snapshot = await db.child("User").child(keyUser).once("value", (snapshot) => {
    //verificar si es correcto el usuario
      if(! snapshot.exists()){
        return res.status(400).json({msg: 'El usuario buscado es incorrecto'})
      }
    });

    //verificar si tiene cupones
    const user = snapshot.val();
    let cuponBuscado = null;

    //verificar si es correcto el cupon
    await db.child("Item").child(keyCoupon).once("value", (snapshot) => {
      if(! snapshot.exists()){
        return res.status(400).json({msg: 'El codigo de cupón es incorrecto'})
      }
    });

    if(user.Cupon !== null && user.Cupon !== undefined ){
      const cupones = Object.values(user.Cupon);
      
      //recorrer el listado y ver si el codigo coincide
      cupones.forEach( (cupon)=>{
        if(cupon.id_item === keyCoupon){
          cuponBuscado = cupon;
        }
      })

      if(cuponBuscado !== null){
        res.json(cuponBuscado);
      }else{
        // si el codigo es correcto pero no pertenece al usuario
        return res.status(401).json({msg: 'Verifique el código de cupón ingresado'})

      }


    }else{
      return res.status(401).json({msg: 'El usuario ingresado no tiene cupones canjeados'})
    }
    
  } catch (error) {
    console.log(error);
    res.status(500).send("Error");
  }
};