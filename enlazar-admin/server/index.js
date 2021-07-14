const express = require('express');
require('dotenv').config({path:'variables.env'})
const firebase = require( "firebase");
const conectFirebase = require('./config/firebase');
const cors = require('cors');

const app = express();
app.use(cors());

conectFirebase();

app.use(express.json({extended: true}))

const PORT = process.env.PORT || 5000;

app.use('/api/user', require('./routes/user'));
app.use('/api/auth', require('./routes/auth'));
app.use('/api/advice', require('./routes/advice'));
app.use('/api/meeting', require('./routes/meeting'));
app.use('/api/coupon', require('./routes/coupon'));
app.use('/api/recycler', require('./routes/recycler'));
app.use('/api/service', require('./routes/service'));

app.listen(PORT, () =>{
    console.log(`El servidor esta funcionando en el ${PORT}`)
})