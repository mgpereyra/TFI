using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Proyecto.Database
{
    public class Recycler
    {

        public string Id { get; set; }

        public string Name { get; set; }

        public string Surname { get; set; }

        [Required(ErrorMessage = "Ingrese un dni.")]
        public int Dni { get; set; }
        public string Address { get; set; }
        public string Password { get; set; }

        [Required(ErrorMessage = "Ingrese un email.")]
        public string Email { get; set; }
        public string Phone { get; set; }
        public string Location { get; set; }
        public string District { get; set; }
        //public UserTypes TypeUser { get; set; }
        public System.DateTime InitDate { get; set; }
        public bool Active { get; set; }
    }
}