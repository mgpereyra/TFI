using Enlazar.Servicios;
using Enlazar_AdminMVC.Models;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Proyecto.Database;
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
            FirebaseResponse response = client.Get("Service");
            dynamic data = JsonConvert.DeserializeObject<dynamic>(response.Body);
            var list = new List<Service>();
            foreach (var item in data)
            {
                list.Add(JsonConvert.DeserializeObject<Service>(((JProperty)item).Value.ToString()));
            }

            return View(list);
        }

        // GET: Service/Details/5
        public ActionResult Details(int id)
        {
            return View();
        }

        // GET: Service/Create
        [HttpPost]
        public ActionResult List(string agregar)
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
            return View();
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
