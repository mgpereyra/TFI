using Enlazar_AdminMVC.Models;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Enlazar_AdminMVC.Controllers
{
    public class RecyclerController : Controller
    {
        IFirebaseConfig config = new FirebaseConfig
        {
            AuthSecret = "Oee3WwwQxBBCDkMBj9habJEBaNKwdx5rvHOJTqNI ",
            BasePath = "https://enlazar-admin-default-rtdb.firebaseio.com/"

        };

        IFirebaseClient client;



        // GET: Recycler/Details/5
        [HttpGet]
        public ActionResult Create()
        {

            return View();
        }


        // POST: Recycler/Create
        [HttpPost]
        public ActionResult Create(Recycler recycler)
        {
            try
            {
                recycler.InitDate = DateTime.Now;
                recycler.Active = true;
                recycler.Password = "123456";
                AddReciyclerToFirebase(recycler);
                ModelState.AddModelError(string.Empty, "Se ha agregado correctamente");
                return RedirectToAction("ListRecyclers");
            }
            catch (Exception e)
            {
                ModelState.AddModelError(string.Empty, e.Message);
                return View();
            }
        }

        private void AddReciyclerToFirebase(Recycler recycler)
        {
            client = new FireSharp.FirebaseClient(config);
            var data = recycler;
            PushResponse response = client.Push("Recyclers/", data);
            data.Id = response.Result.name;
            SetResponse setResponse = client.Set("Recyclers/" + response.Result.name, data);

        }



        // GET: Recycler
        public ActionResult ListRecyclers()
        {
            client = new FireSharp.FirebaseClient(config);
            FirebaseResponse response = client.Get("Recyclers");
            dynamic data = JsonConvert.DeserializeObject<dynamic>(response.Body);

            var list = new List<Recycler>();
            foreach( var item in data)
            {
                list.Add(JsonConvert.DeserializeObject<Recycler>(((JProperty)item).Value.ToString()));
            }


            return View(list);
        }

        // GET: Recycler/Details/5
        [HttpGet]
        public ActionResult Details(string id)
        {
            client = new FireSharp.FirebaseClient(config);
            FirebaseResponse response = client.Get("Recyclers/"+ id);
            Recycler data = JsonConvert.DeserializeObject<Recycler>(response.Body);


            return View(data);
        }
       

        // GET: Recycler/Edit/5
        [HttpGet]
        public ActionResult Edit(string id)
        {
                client = new FireSharp.FirebaseClient(config);
                FirebaseResponse response = client.Get("Recyclers/" + id);
                Recycler data = JsonConvert.DeserializeObject<Recycler>(response.Body);

                return View(data);
            
        }

        // POST: Recycler/Edit/5
        [HttpPost]
        public ActionResult Edit(Recycler recycler)
        {
            client = new FireSharp.FirebaseClient(config);
            SetResponse response = client.Set("Recyclers/" + recycler.Id, recycler);

            return RedirectToAction("ListRecyclers");

        }

        // GET: Recycler/Delete/5
        [HttpGet]
        public ActionResult Delete(string id)
        {
            client = new FireSharp.FirebaseClient(config);
            FirebaseResponse response = client.Delete("Recyclers/" + id);

            return RedirectToAction("ListRecyclers");
        }
    }
}
