package ar.com.unlam.enlazar.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class NewServiceViewModel(private val estado:SavedStateHandle):ViewModel() {
    val estados = MutableLiveData<EstadoNewService>()








    enum class EstadoNewService {
        SUCCESS,
        ERROR
    }
}