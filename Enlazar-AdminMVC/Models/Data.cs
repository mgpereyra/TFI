using Enlazar.Database;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Enlazar_AdminMVC.Models
{
    public class Data
    {

        IFirebaseClient client;
        public IFirebaseClient GetConnection()
        {
            IFirebaseConfig config = new FirebaseConfig
            {
                AuthSecret = "Oee3WwwQxBBCDkMBj9habJEBaNKwdx5rvHOJTqNI ",
                BasePath = "https://enlazar-admin-default-rtdb.firebaseio.com/"

            };
            client = new FireSharp.FirebaseClient(config);

            return client;

        }

    }
}