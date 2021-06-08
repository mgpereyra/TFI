using Enlazar.Database.ViewModels;
using Enlazar_AdminMVC.Models;
using System;
using System.Web.Mvc;
using System.Web;


namespace Enlazar_AdminMVC.Controllers

{
    public class ServiceController : Controller
    {
        private readonly RecyclerModel recyclerModel = new RecyclerModel();
        private readonly ServiceModel serviceModel = new ServiceModel();
        private readonly ServiceViewModel serviceViewModel = new ServiceViewModel();
        // GET: Service/Manage
        [HttpGet]
        public ActionResult Manage()
        {
            serviceViewModel.ListServices = serviceModel.GetServicesPendings();
            serviceViewModel.ListRecyclers = recyclerModel.GetRecyclers();

            return View(serviceViewModel);
        }

      
        // GET: Service/Manage
        [HttpPost]
        public ActionResult Manage(string  servicio, string reciclador)
        {
            try
            {
                string[] listaServicios = servicio.Split(',');

                if (ModelState.IsValid)
                {
                    serviceModel.EditListServices(listaServicios, reciclador);
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
        [HttpGet]
        public ActionResult List()
        {
            serviceViewModel.ListServices = serviceModel.GetServicesAssigned();

            return View(serviceViewModel);
        }

        // GET: Service/Details/5
        public ActionResult Details(int id)
        {
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
            serviceViewModel.ListServices = serviceModel.GetServicesAssigned();

            return View(serviceViewModel);
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
