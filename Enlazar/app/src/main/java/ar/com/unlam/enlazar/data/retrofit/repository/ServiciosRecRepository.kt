package ar.com.unlam.enlazar.data.retrofit.repository

import ar.com.unlam.enlazar.model.Service

interface ServiciosRecRepository {
   fun retList(): ArrayList<Service>
   suspend fun getServiciosPendientes()
}