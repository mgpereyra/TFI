package ar.com.unlam.enlazar.ui.vecino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ar.com.unlam.enlazar.R
import ar.com.unlam.enlazar.model.Service
import ar.com.unlam.enlazar.model.User
import ar.com.unlam.enlazar.ui.LoginActivity
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_crear_cuenta.*
import kotlinx.android.synthetic.main.activity_crear_cuenta.email
import kotlinx.android.synthetic.main.activity_crear_cuenta.password
import kotlinx.android.synthetic.main.activity_crear_cuenta.view.*
import kotlinx.android.synthetic.main.activity_login.*
import java.text.SimpleDateFormat
import java.util.*

class CrearCuentaActivity : AppCompatActivity() {
    private val db = FirebaseDatabase.getInstance().getReference("User")
    lateinit var mPlaces: PlacesClient
    var mOriginLat: Double? = null
    var mOriginLng: Double? = null
    var mAdress: String? = null
    var id: String = ""
    var mAutocomplete: AutocompleteSupportFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)
        setOnClickListeners()
        setup()
        setUpPlaces()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }
    private fun setOnClickListeners() {
        btnVolver.setOnClickListener {
           irDashboardLoginActivity()
        }
        /* btn_login.setOnClickListener {
             setup()
             val intent = Intent(this, DashboardUserActivity::class.java)
             this@CrearCuentaActivity.finish()
         }*/
    }

    private fun setup() {
        btn_crear_cuenta.setOnClickListener {
            if (validarCamposObligatorios()) {
                val mailString = email.editText?.text.toString().trim()
                val passString = password.editText?.text.toString()
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    mailString,
                    passString
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        createUser()
                        irDashboardLoginActivity()
                    } else {
                        showAlert()
                    }
                }
            }

        }
    }

    private fun validarCamposObligatorios(): Boolean {

        if (email.editText?.text.toString().isNotEmpty()) {
            if (name.editText?.text.toString().isNotEmpty()) {
                if (!mAdress.equals(null)) {
                    if (location.editText?.text.toString().isNotEmpty()) {
                        if (partido.editText?.text.toString().isNotEmpty()) {
                            if (telephone.editText?.text.toString().isNotEmpty()) {
                                if (dni.editText?.text.toString().isNotEmpty()) {
                                    if (password.editText?.text.toString().isNotEmpty()) {
                                        return true
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Es necesario completar la Password (minimo siete dígitos)",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return false
                                    }
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Es necesario completar el campo DNI",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return false
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Es necesario completar el campo Teléfono",
                                    Toast.LENGTH_LONG
                                ).show()
                                return false
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "Es necesario completar el campo Partido",
                                Toast.LENGTH_LONG
                            ).show()
                            return false
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Es necesario completar el campo Localidad",
                            Toast.LENGTH_LONG
                        ).show()
                        return false
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Es necesario completar el campo Buscar",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    return false
                }
            } else {
                Toast.makeText(this, "Es necesario completar el campo nombre", Toast.LENGTH_LONG)
                    .show()
                return false
            }
        } else {
            Toast.makeText(
                this,
                "Es necesario completar el email",
                Toast.LENGTH_LONG
            ).show()
            return false
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
                mAdress = place.name
                mOriginLat = place.latLng?.latitude
                mOriginLng = place.latLng?.longitude

            }

            override fun onError(place: Status) {
                Toast.makeText(
                    this@CrearCuentaActivity,
                    getString(R.string.place_not_found), Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun irDashboardLoginActivity() {
        val darsheboardActivity = Intent(this, LoginActivity::class.java)
        this.startActivity(darsheboardActivity)
            finish()
        /*.apply {
     putExtra("email",email)
     putExtra("provider",provider)
    }*/
        //startActivity(darsheboardActivity)
    }

    private fun createUser() {
        val emptyArray = ArrayList<Service>()
        val date = getCurrentDateTime()
        val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
        //var userId = db.push().key.toString()
        id = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
        var user = User(
            mAdress.toString(),
            partido.editText?.text.toString(),
            dni.editText?.text.toString(),
            email.editText?.text.toString().trim(),
            id,
            dateInString,
            mOriginLat.toString(),
            mOriginLng.toString(),
            location.editText?.text.toString(),
            name.editText?.text.toString(),
            password.editText?.text.toString(),
            telephone.editText?.text.toString()
        )
        db.child(id).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Te has registrado correctamente", Toast.LENGTH_LONG).show()
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