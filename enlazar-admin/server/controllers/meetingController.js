const firebase = require( "firebase");
const {validationResult}= require('express-validator');

//obtener consejos creados por el usuario actual
exports.getListMeeting = async(req, res) => {
    try {
        const db = firebase.database().ref();
        const list = []

        await db.child('MeetingPoint').orderByChild("date").once("value",snapshot => {
            
            if(snapshot.exists()){
                snapshot.forEach((snap) => {
                const value = snap.val()

               // value.dates = d;
              
                list.push(value)
                list.sort(function(a, b) {
                    return b.date - a.date;
                    });
                })
            }    

        });
     
        //validando existencia
       
           res.json(list)
    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}

exports.createMeeting = async (req, res) =>{
    const {  date, description, title, time, lat, lng, ubication } = req.body;

    const errors = validationResult(req);

    if(!errors.isEmpty()){
        return res.status(400).json({errors: errors.array()})
    }

    try {
        const idUser = req.user.id;

        const ref = firebase.database().ref('MeetingPoint').push();

        const save = ref.set({
            date, description, title, time, estado: 1,  id : ref.key,
            lat, lng, ubication
        });
        
        res.send(save)

    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}

//Modificar meeting
exports.putMeeting = async(req, res) => {
    try {
        const key = req.params.id;
        const postData = req.body

        const db = firebase.database().ref();
        await db.child('MeetingPoint').child(key).update(postData)
        
        res.json({msg: 'se modifico correctamente'})
            
    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}

//Eliminar un consejo
exports.deleteMeeting = async(req, res) => {
    try {
        const db= firebase.database().ref();
        
        //verificar i existe el consejo
        //verificar que el usuario sea el propietario

        await db.child('MeetingPoint').child(req.params.id).remove()
        res.json({msg: 'Consejo eliminado'})
    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}


//obtener consejos via parametros
exports.getMeeting = async (req, res) => {
    try {
      const db = firebase.database().ref();
      const key = req.params.id;
  
      const snapshot = await db.child("MeetingPoint").child(key).once("value", (snapshot) => {
        return snapshot;
      });
      //validando existencia
      if (snapshot.exists()) {
        //error
        res.json(snapshot.val());
      }else{
        res.status(500).json({msg:"El punto de encuentro buscado no es válido"});
      }
    } catch (error) {
      console.log(error);
      res.status(500).send("Error");
    }
  };