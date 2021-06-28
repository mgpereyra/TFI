const firebase = require( "firebase");
const {validationResult}= require('express-validator');

//obtener consejos creados por el usuario actual
exports.getListMeeting = async(req, res) => {
    try {
        const db = firebase.database().ref();
        const snapshot = await db.child('MeetingPoint').orderByChild("date").once("value",snapshot => {
            return snapshot
        });
     
        //validando existencia
        if(snapshot.exists()){
           res.json(snapshot.val())
        }        
    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}

exports.createMeeting = async (req, res) =>{
    const { calle, date, description, localidad, lugar, time, estado } = req.body;

    const errors = validationResult(req);

    if(!errors.isEmpty()){
        return res.status(400).json({errors: errors.array()})
    }

    try {
        const idUser = req.user.id;

        const ref = firebase.database().ref('MeetingPoint').push();

        ref.set({
            calle, date, description, localidad, lugar, time, estado,  id : ref.key
        });
        
        //res.send(save)

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
