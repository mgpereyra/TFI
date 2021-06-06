
using Enlazar.Database.Utilities;
using Enlazar_AdminMVC.Models;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Proyecto.Database;
using System;
using System.Collections.Generic;
using System.Web.Mvc;

namespace Enlazar_AdminMVC.Controllers
{
    public class RecyclerController : Controller
    {
        IFirebaseClient client;
         Data database = new Data();             

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
                List<Service> services = new List<Service>();

                services.Add(new Service() { Title = "nuevoServicio" });
                services.Add(new Service() { Title = "nuevoServicioDos" });


                if (ModelState.IsValid)
                {
                    recycler.InitDate = DateTime.Now;
                    recycler.Active = true;
                    recycler.Password = "123456";
                    recycler.Services = services;
                    recycler.TypeUser = UserTypes.RECYCLER;
                    database.AddReciyclerToFirebase(recycler);
                    ModelState.AddModelError(string.Empty, "Se ha agregado correctamente");
                    return RedirectToAction("ListRecyclers");
                }
                else
                {
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
            foreach( var item in data)
            {
                //if (item[0].TypeUser == 2) { };
                list.Add(JsonConvert.DeserializeObject<User>(((JProperty)item).Value.ToString()));
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
            SetResponse response = client.Set("User/" + recycler.Id, recycler);

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
