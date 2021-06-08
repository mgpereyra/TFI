using Enlazar.Database;
using Enlazar.Database.Utilities;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;

namespace Enlazar_AdminMVC.Models
{
    public class ServiceModel
    {
        IFirebaseClient client;
        Data database = new Data();
        private readonly RecyclerModel recyclerModel = new RecyclerModel();

        //GET RECYCLERS
        public List<Service> GetServicesAssigned()
        {
            client = database.GetConnection();
            FirebaseResponse response = client.Get("Service");

            dynamic data = JsonConvert.DeserializeObject<dynamic>(response.Body);
            var listservices = new List<Service>();
            Service service;
            User recycler;
            User user;

            foreach (var item in data)
            {
                service = JsonConvert.DeserializeObject<Service>(((JProperty)item).Value.ToString());
               
                if (service.Estado == StateServices.ASIGNADO) { 

                    recycler = recyclerModel.GetRecycler(service.RecolectorId);
                    user = recyclerModel.GetRecycler(service.UserId);
                    service.RecolectorId = recycler.Name + " " + recycler.Surname;
                    service.UserId = user.Name + " " + user.Surname;
                
                    listservices.Add(service);
                }
            }

            return listservices;
        }
        public List<Service> GetServicesPendings()
        {
            client = database.GetConnection();
            FirebaseResponse response = client.Get("Service");

            dynamic data = JsonConvert.DeserializeObject<dynamic>(response.Body);
            var listservices = new List<Service>();
            Service service;

            foreach (var item in data)
            {
                service = JsonConvert.DeserializeObject<Service>(((JProperty)item).Value.ToString());
                if (service.Estado == StateServices.PENDIENTE)
                {
                    listservices.Add(service);

                }
            }

            return listservices;
        }
        public void EditListServices(string [] listaServicios, string idRecycler)
        {
            client = database.GetConnection();
            foreach (var item in listaServicios)
            {
                FirebaseResponse response = client.Get("Service/" + item);
                Service anterior = JsonConvert.DeserializeObject<Service>(response.Body);
                anterior.RecolectorId = idRecycler;
                anterior.Estado = StateServices.ASIGNADO;

                SetResponse setResponse = client.Set("Service/" + item, anterior);
            }
        }
        public List<Service> GetAllServices()
        {
            client = database.GetConnection();
            FirebaseResponse response = client.Get("Service");

            dynamic data = JsonConvert.DeserializeObject<dynamic>(response.Body);
            var listservices = new List<Service>();
            Service service;
            User recycler;
            User user;

            foreach (var item in data)
            {
                service = JsonConvert.DeserializeObject<Service>(((JProperty)item).Value.ToString());

                if (service.Estado != StateServices.FINALIZADO)
                {

                    recycler = recyclerModel.GetRecycler(service.RecolectorId);
                    user = recyclerModel.GetRecycler(service.UserId);
                    service.RecolectorId = recycler.Name + " " + recycler.Surname;
                    service.UserId = user.Name + " " + user.Surname;

                    listservices.Add(service);
                }
            }

            return listservices;
        }

    }
}