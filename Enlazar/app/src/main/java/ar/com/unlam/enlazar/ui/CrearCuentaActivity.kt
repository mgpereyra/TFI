package ar.com.unlam.enlazar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.Services
import ar.com.unlam.enlazar.model.User
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_crear_cuenta.*
import kotlinx.android.synthetic.main.activity_crear_cuenta.view.*
import java.text.SimpleDateFormat
import java.util.*

class CrearCuentaActivity : AppCompatActivity() {
 private val db= FirebaseDatabase.getInstance().getReference("User")
    lateinit var mPlaces:PlacesClient
     var mOriginLat:Double? = null
    var mOriginLng:Double? = null
    var mAdress:String? = null
    var id:String=""
     var  mAutocomplete:AutocompleteSupportFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)
        btnVolver.setOnClickListener {
            this@CrearCuentaActivity.finish()
        }
        btn_login.setOnClickListener{

            setup()

            val intent= Intent(this, DashboardUserActivity::class.java)
            this@CrearCuentaActivity.finish()



        }
        setup()
        setUpPlaces()
    }

    private fun setup(){
        btn_login.setOnClickListener {
            if(email.editText?.text.toString().isNotEmpty() && password.editText?.text.toString().isNotEmpty()){
                val mailString = email.editText?.text.toString()
                val passString = password.editText?.text.toString()
                 FirebaseAuth.getInstance().
                 createUserWithEmailAndPassword(mailString,
                     passString).addOnCompleteListener{
                    if (it.isSuccessful){
                        createUser()
                        irDashboardUserActivity()
                    }else{
                        showAlert()
                    }
                 }
             }
        }
    }
    private fun setUpPlaces(){
        if(!Places.isInitialized()){
            Places.initialize(applicationContext,resources.getString(R.string.google_maps_key))

        }
        mPlaces=Places.createClient(this)
        mAutocomplete= supportFragmentManager?.findFragmentById(R.id.streetAutocompleteOrigin)  as? AutocompleteSupportFragment
        mAutocomplete?.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG,Place.Field.NAME))
        mAutocomplete?.setOnPlaceSelectedListener(object:PlaceSelectionListener{
            override fun onPlaceSelected(place: Place) {
                mAdress= place.name
                mOriginLat=place.latLng?.latitude
                mOriginLng=place.latLng?.longitude

            }

            override fun onError(place: Status) {
                Toast.makeText(this@CrearCuentaActivity,
                    getString(R.string.place_not_found), Toast.LENGTH_LONG).show()
            }


        })


    }
private fun showAlert(){
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Error")
    builder.setMessage("Se ha producido un error autenticando al usuario")
    builder.setPositiveButton("Aceptar",null)
    val dialog: AlertDialog=builder.create()
    dialog.show()

}

    private fun irDashboardUserActivity(){
        val darsheboardActivity = Intent(this,DashboardUserActivity::class.java)
        darsheboardActivity.putExtra(DashboardUserActivity.IDKEY,id!!)

        this.startActivity(darsheboardActivity)
            /*.apply {
         putExtra("email",email)
         putExtra("provider",provider)
        }*/
        //startActivity(darsheboardActivity)
    }

private fun createUser(){
    val emptyArray=ArrayList<Services>()
    val date = getCurrentDateTime()
    val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
    var userId= db.push().key.toString()
   id=FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
    var user=User(mAdress.toString(),partido.editText?.text.toString(),
       dni.editText?.text.toString().toInt(),email.editText?.text.toString(),
       userId,dateInString,mOriginLat,mOriginLng,location.editText?.text.toString(),
        name.editText?.text.toString(), password.editText?.text.toString(),
       telephone.editText?.text.toString(),Service = emptyArray)
    if (userId != null) {
        db.child(id).setValue(user).addOnCompleteListener{
            Toast.makeText(this, "Te has registrado correctamente",Toast.LENGTH_LONG).show()

        }
    }


}
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}