using Enlazar.Database;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.Collections.Generic;

namespace Enlazar_AdminMVC.Models
{
    public class ServiceModel
    {
        IFirebaseClient client;
        Data database = new Data();
        //GET RECYCLERS
        public List<Service> GetServices()
        {
            client = database.GetConnection();
            FirebaseResponse response = client.Get("Service");

            dynamic data = JsonConvert.DeserializeObject<dynamic>(response.Body);
            var listservices = new List<Service>();

            foreach (var item in data)
            {

                listservices.Add(JsonConvert.DeserializeObject<Service>(((JProperty)item).Value.ToString()));
            }

            return listservices;
        }

    }
}