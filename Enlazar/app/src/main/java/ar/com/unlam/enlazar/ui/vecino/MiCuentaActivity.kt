package ar.com.unlam.enlazar.ui.vecino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ar.com.unlam.enlazar.R
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_mi_cuenta.*
import java.util.*
import kotlin.properties.Delegates

class MiCuentaActivity : AppCompatActivity() {
    private val db = FirebaseDatabase.getInstance().getReference()
    var id = FirebaseAuth.getInstance().currentUser!!.uid
    var address: String?=null
    var lat :Double?=null
    var long:Double?=null
    lateinit var mPlaces: PlacesClient
    var mAutocomplete: AutocompleteSupportFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_cuenta)
        setUpPlaces()
        getUsuario(id)
        btnVolver_deMiCuenta.setOnClickListener {
            finish()
        }
        btn_guardar_cambios.setOnClickListener {
            actualizarCuenta()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }

    private fun actualizarCuenta() {
        if (address?.isNotEmpty() == true && long.toString().isNotEmpty() && lat.toString().isNotEmpty()) {
            db.child("User").child(id).child("latitud").setValue(lat.toString())
            db.child("User").child(id).child("longitud").setValue(long.toString())
            db.child("User").child(id).child("address").setValue(address)
            db.child("User").child(id).child("locality")
                .setValue(localidad.editText?.text.toString())
            db.child("User").child(id).child("name").setValue(name.editText?.text.toString())
                .addOnCompleteListener {
                    Toast.makeText(this, "Cambios Guardados", Toast.LENGTH_LONG).show()
                }
            finish()
        } else {
            Toast.makeText(
                this,
                "No puede actualizar la cuenta sin una direccion ingresada",
                Toast.LENGTH_LONG
            ).show()

        }

    }

    private fun setUpPlaces() {
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, resources.getString(R.string.google_maps_key))

        }
        mPlaces = Places.createClient(this)
        mAutocomplete =
            supportFragmentManager?.findFragmentById(R.id.streetAutocompleteOrigin) as? AutocompleteSupportFragment
        mAutocomplete?.setPlaceFields(
            Arrays.asList(
                Place.Field.ID,
                Place.Field.LAT_LNG,
                Place.Field.NAME
            )
        )
        mAutocomplete?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                address = place.name!!
                lat = place.latLng?.latitude!!
                long = place.latLng?.longitude!!

            }

            override fun onError(place: Status) {
                Toast.makeText(
                    this@MiCuentaActivity,
                    getString(R.string.place_not_found), Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun getUsuario(idUser: String) {
        db.child("User").child(idUser).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                container_datos.visibility = View.VISIBLE
                sin_datos.visibility = View.GONE
                mAutocomplete?.setText(snapshot.child("address").value.toString())
                lat = snapshot.child("latitud").value.toString().toDouble()
                long = snapshot.child("longitud").value.toString().toDouble()
                localidad.editText?.setText(snapshot.child("locality").value.toString())
                name.editText?.setText(snapshot.child("name").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                container_datos.visibility = View.GONE
                sin_datos.visibility = View.VISIBLE
                Toast.makeText(
                    this@MiCuentaActivity,
                    "Debes tener al menos una bolsa con material para reciclar",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}