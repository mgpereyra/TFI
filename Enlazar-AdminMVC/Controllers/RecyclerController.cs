
using Enlazar.Database;
using Enlazar.Database.Utilities;
using Enlazar.Servicios;
using Enlazar_AdminMVC.Models;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Web.Mvc;
using static Enlazar_AdminMVC.Utilities.Utilities;

namespace Enlazar_AdminMVC.Controllers
{
    public class RecyclerController : Controller
    {
        IFirebaseClient client;
        Data database = new Data();
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        RecyclerService recyclerservice = new RecyclerService();

        // GET: Recycler/Details/5
        [HttpGet]
        public ActionResult Create()
        {

            return View();
        }


        // POST: Recycler/Create
        [HttpPost]
        public ActionResult Create(User recycler)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    recycler.InitDate = DateTime.Now;
                    recycler.Active = true;
                    recycler.Password = passwordGenerator.RandomPassword();
                    recycler.TypeUser = UserTypes.RECYCLER;
                    database.AddReciyclerToFirebase(recycler);
                    ModelState.AddModelError(string.Empty, "Se ha agregado correctamente");
                    return RedirectToAction("ListRecyclers");
                }
                else
                {
                    //error
                    return View();
                }

            }
            catch (Exception e)
            {
                ModelState.AddModelError(string.Empty, e.Message);
                return View();
            }
        }

  


        // GET: Recycler
        public ActionResult ListRecyclers()
        {

            client = new FireSharp.FirebaseClient(database.GetRecyclers());
            FirebaseResponse response = client.Get("User");

            dynamic data = JsonConvert.DeserializeObject<dynamic>(response.Body);
            var list = new List<User>();
            User user;

            foreach( var item in data)
            {
                user = JsonConvert.DeserializeObject<User>(((JProperty)item).Value.ToString());

                if (user.TypeUser == UserTypes.RECYCLER) { 
                    list.Add(JsonConvert.DeserializeObject<User>(((JProperty)item).Value.ToString()));
                };
            }


            return View(list);
        }

        // GET: Recycler/Details/5
        [HttpGet]
        public ActionResult Details(string id)
        {
            client = new FireSharp.FirebaseClient(database.createConfig());
            FirebaseResponse response = client.Get("User/"+ id);
            User data = JsonConvert.DeserializeObject<User>(response.Body);


            return View(data);
        }
       

        // GET: Recycler/Edit/5
        [HttpGet]
        public ActionResult Edit(string id)
        {
                client = new FireSharp.FirebaseClient(database.createConfig());
                FirebaseResponse response = client.Get("User/" + id);
                User data = JsonConvert.DeserializeObject<User>(response.Body);

            return View(data);
            
        }

        // POST: Recycler/Edit/5
        [HttpPost]
        public ActionResult Edit(User recycler)
        {
            client = new FireSharp.FirebaseClient(database.createConfig());
            FirebaseResponse response = client.Get("User/" + recycler.Id);
            User anterior = JsonConvert.DeserializeObject<User>(response.Body);

            recyclerservice.ActualizarCampos(anterior, recycler);

            SetResponse setResponse = client.Set("User/" + recycler.Id, anterior);

            return RedirectToAction("ListRecyclers");

        }

        // GET: Recycler/Delete/5
        [HttpGet]
        public ActionResult Delete(string id)
        {
            client = new FireSharp.FirebaseClient(database.createConfig());
            FirebaseResponse response = client.Delete("User/" + id);

            return RedirectToAction("ListRecyclers");
        }
    }
}
