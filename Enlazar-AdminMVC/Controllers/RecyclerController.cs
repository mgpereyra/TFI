
using Enlazar.Database;
using Enlazar_AdminMVC.Models;
using System;
using System.Collections.Generic;
using System.Web.Mvc;

namespace Enlazar_AdminMVC.Controllers
{
    public class RecyclerController : Controller
    {
        RecyclerModel recyclerModel = new RecyclerModel();

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
                    User recyclerOk = recyclerModel.CreateRecycler(recycler);
                    ModelState.AddModelError(string.Empty, "Se ha agregado correctamente");
                    return RedirectToAction("ListRecyclers");
                }
                else
                {
                    //mensaje error
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
            List <User> list = recyclerModel.GetUsers();
            return View(list);
        }

        // GET: Recycler/Details/5
        [HttpGet]
        public ActionResult Details(string id)
        {
            User data = recyclerModel.GetRecycler(id);
            return View(data);
        }
       

        // GET: Recycler/Edit/5
        [HttpGet]
        public ActionResult Edit(string id)
        {
            User data = recyclerModel.GetRecycler(id);
            return View(data);
        }

        // POST: Recycler/Edit/5
        [HttpPost]
        public ActionResult Edit(User recycler)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    User recyclerOk = recyclerModel.EditRecycler(recycler);
                    ModelState.AddModelError(string.Empty, "Se ha editado correctamente");
                    return RedirectToAction("ListRecyclers");
                }
                else
                {
                    //mensaje error
                    return View();
                }
            }
            catch (Exception e)
            {
                ModelState.AddModelError(string.Empty, e.Message);
                return View();
            }
        }

        // GET: Recycler/Delete/5
        [HttpGet]
        public ActionResult Delete(string id)
        {
            recyclerModel.Delete(id);
            return RedirectToAction("ListRecyclers");
        }
    }
}
