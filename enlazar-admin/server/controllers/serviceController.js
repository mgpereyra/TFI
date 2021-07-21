const firebase = require( "firebase");
const {validationResult}= require('express-validator');

//obtener servicios 
exports.getListServices = async(req, res) => {
    try {
        const db = firebase.database().ref();
        const list = []
        const date = new Date()
        const snapshot = await db.child('Service').once("value",snapshot => {
            snapshot.forEach((snap) => {
               const value = snap.val()

               var date1 = (value.date).split("/");
               var d = new Date(date1[2], date1[1] - 1, date1[0]);
               
               value.dates = d;
               value.date = d.toLocaleDateString('en-GB');
              
                list.push(value)
               // console.log(list)
                list.sort(function(a, b) {
                    var dateA = new Date(a.dates)
                    var  dateB = new Date(b.dates)
                    return b.dates - a.dates;
                  });
            })

           res.json(list)
        });
     
        //validando existencia
        //if(snapshot.exists()){
         ///  res.json(snapshot.val())
        //}        
    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}


//obtener servicios pendientes
exports.getListServicesPendings = async(req, res) => {
    try {
        const db = firebase.database().ref();
        const list = []
        const snapshot = await db.child('Service').orderByChild('estado').equalTo(0).once("value",snapshot => {
            snapshot.forEach((snap) => {
                const value = snap.val()

                var date1 = (value.date).split("/");
                var d = new Date(date1[2], date1[1] - 1, date1[0]);
                
                value.dates = d;
                value.date = d.toLocaleDateString('en-GB');
              
                list.push(value)
                list.sort(function(a, b) { return b.dates - a.dates; });
            })

           res.json(list)
        });
    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}


//Modificar servicio
exports.createAssignament = async(req, res) => {
    try {
       
        const recycler = req.body 
        const key = req.params.id

        //console.log(recycler.recycler)
        const db = firebase.database().ref();
        await db.child('Service').child(key).update({ "recolectorId": recycler.recycler , "estado": 1})
        
        res.json({msg: 'Se modifico correctamente'})
            
    } catch (error) {
        console.log(error)
        res.status(500).send('Error')
    }
}