using Enlazar.Database;
using Enlazar.Database.Utilities;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using static Enlazar_AdminMVC.Utilities.Utilities;

namespace Enlazar_AdminMVC.Models
{
    public class RecyclerModel
    {
        IFirebaseClient client;
        private readonly Data database = new Data();
        private readonly PasswordGenerator passwordGenerator = new PasswordGenerator();

        //GET RECYCLERS
        public List<User> GetRecyclers()
        {
            client = database.GetConnection();
            FirebaseResponse response = client.Get("User");

            dynamic data = JsonConvert.DeserializeObject<dynamic>(response.Body);
            List<User> listRecyclers = new List<User>();
            User user;

            foreach (var item in data)
            {
                user = JsonConvert.DeserializeObject<User>(((JProperty)item).Value.ToString());

                if (user.typeUser == UserTypes.RECYCLER)
                {
                    listRecyclers.Add(user);
                };
            }

            return listRecyclers;
        }

        public User CreateRecycler(User recycler)
        {
            recycler.initDate = DateTime.Now;
            recycler.active = true;
            recycler.password = passwordGenerator.RandomPassword();
            recycler.typeUser = UserTypes.RECYCLER;

            var data = recycler;
            PushResponse response = client.Push("User/", data);
            data.id = response.Result.name;
            SetResponse setResponse = client.Set("User/" + response.Result.name, data);
            return recycler;
        }

        public User GetRecycler(string id)
        {
            client = database.GetConnection();
            FirebaseResponse response = client.Get("User/" + id);
            User data = JsonConvert.DeserializeObject<User>(response.Body);

            return data;
        }
        public User EditRecycler(User recycler)
        {
            client = database.GetConnection();
            FirebaseResponse response = client.Get("User/" + recycler.id);
            User anterior = JsonConvert.DeserializeObject<User>(response.Body);
            ActualizarCampos(anterior, recycler);

            SetResponse setResponse = client.Set("User/" + recycler.id, anterior);

            return anterior;
        }

        public void Delete(string id)
        {
            client = database.GetConnection();
            FirebaseResponse response = client.Delete("User/" + id);
        }

        public void ActualizarCampos(User anterior, User recycler)
        {
            anterior.address = recycler.address;
            anterior.district = recycler.district;
            anterior.dni = recycler.dni;
            anterior.email = recycler.email;
            anterior.name = recycler.name;
            anterior.surname = recycler.surname;
            anterior.phone = recycler.phone;
            anterior.locality = recycler.locality;
        }

    }
}