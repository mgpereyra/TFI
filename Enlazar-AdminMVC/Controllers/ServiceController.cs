using Enlazar.Database;
using Enlazar.Database.Utilities;
using Enlazar.Database.ViewModels;
using Enlazar.Servicios;
using Enlazar_AdminMVC.Models;
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
    public class ServiceController : Controller
    {
        Data database = new Data();
        IFirebaseClient client;



        // GET: Service
        [HttpGet]
        public ActionResult List()
        {
            client = new FireSharp.FirebaseClient(database.createConfig());
            // database.AddServiceToFirebase();

            ServiceViewModel serviceViewModel = new ServiceViewModel();

            FirebaseResponse response = client.Get("Service");
            FirebaseResponse responseUser = client.Get("User");


            dynamic data = JsonConvert.DeserializeObject<dynamic>(response.Body);
            dynamic dataUser = JsonConvert.DeserializeObject<dynamic>(responseUser.Body);

            var listServices = new List<Service>();
            var listRecyclers = new List<User>();
            User user;

            foreach (var item in data)
            {
                listServices.Add(JsonConvert.DeserializeObject<Service>(((JProperty)item).Value.ToString()));
            }

            foreach (var item in dataUser)
            {
                user = JsonConvert.DeserializeObject<User>(((JProperty)item).Value.ToString());

                if (user.TypeUser == UserTypes.RECYCLER)
                {
                    listRecyclers.Add(user);
                };
            }

            serviceViewModel.ListServices = listServices;
            serviceViewModel.ListRecyclers = listRecyclers;

            return View(serviceViewModel);
        }

        // GET: Service/Details/5
        public ActionResult Details(int id)
        {
            return View();
        }

        // GET: Service/Create
        [HttpPost]
        public ActionResult List(string servicio, string reciclador)
        {
            try
            {

                if (ModelState.IsValid)
                {
                    //database.AddServiceToRoute(service);
                    ModelState.AddModelError(string.Empty, "Se ha agregado correctamente");
                    return RedirectToAction("List");
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

        // POST: Service/Create
        [HttpPost]
        public ActionResult Create(FormCollection collection)
        {
            try
            {
                // TODO: Add insert logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        // GET: Service/Edit/5
        public ActionResult Edit(int id)
        {
            return View();
        }

        // POST: Service/Edit/5
        [HttpPost]
        public ActionResult Edit(int id, FormCollection collection)
        {
            try
            {
                // TODO: Add update logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        // GET: Service/Delete/5
        public ActionResult Delete(int id)
        {
            return View();
        }

        // POST: Service/Delete/5
        [HttpPost]
        public ActionResult Delete(int id, FormCollection collection)
        {
            try
            {
                // TODO: Add delete logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }
    }
}
