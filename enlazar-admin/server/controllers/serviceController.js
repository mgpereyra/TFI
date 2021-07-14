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
/*
address, 
calculado, 
comentario,
date,
envasesCarton,
envasesPlasticos,
envasesVidrio,
estado,
id,
latitud,
longitud, 
recolectorId,
time
*/
