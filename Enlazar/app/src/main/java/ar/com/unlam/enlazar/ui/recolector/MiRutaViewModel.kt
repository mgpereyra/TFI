package ar.com.unlam.enlazar.ui.recolector

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.com.unlam.enlazar.model.Services

class MiRutaViewModel : ViewModel() {
    val misServicios = MutableLiveData<Services>()


    fun getServices(){

    }

}