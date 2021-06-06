using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Proyecto.Database;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Enlazar_AdminMVC.Models
{
    public class Data
    {

        IFirebaseClient client;

        public IFirebaseConfig createConfig()
        {
            IFirebaseConfig config = new FirebaseConfig
            {
                AuthSecret = "Oee3WwwQxBBCDkMBj9habJEBaNKwdx5rvHOJTqNI ",
                BasePath = "https://enlazar-admin-default-rtdb.firebaseio.com/"

            };
            return config;

        }
        public IFirebaseConfig GetRecyclers()
        {
            char chard = (char)34;
            string orderBy = "TypeUser";

            string path = "https://enlazar-admin-default-rtdb.firebaseio.com/User.json?orderBy="+orderBy;
            IFirebaseConfig config = new FirebaseConfig
            {
                AuthSecret = "Oee3WwwQxBBCDkMBj9habJEBaNKwdx5rvHOJTqNI ",
                BasePath = path
                //BasePath = "https://enlazar-admin-default-rtdb.firebaseio.com/User.json?\"orderBy\"=\"TypeUser\"&\"equalTo\"=\"2\""

            };

            //config.BasePath.
            return config;

        }
        public Service AddServiceToFirebase()
        {
            Service service = new Service();
            service.Title = "rutauno";



            client = new FireSharp.FirebaseClient(createConfig());
            var data = service;
            PushResponse response = client.Push("Service/", data);
            data.Id = response.Result.name;
            SetResponse setResponse = client.Set("Service/" + response.Result.name, data);

            return service;
        }
        public void AddReciyclerToFirebase(User recycler)
        {
            client = new FireSharp.FirebaseClient(createConfig());
            var data = recycler;
            PushResponse response = client.Push("User/", data);
            data.Id = response.Result.name;
            SetResponse setResponse = client.Set("User/" + response.Result.name, data);

        }




    }
}