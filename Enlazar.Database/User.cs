using Enlazar.Database;
using Enlazar.Database.Utilities;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Enlazar.Database
{
    public class User
    {

        public string Id { get; set; }
        public string Name { get; set; }
        public string Surname { get; set; }

        [Required(ErrorMessage = "Ingrese un dni.")]
        public int Dni { get; set; }
        public string Password { get; set; }

        [Required(ErrorMessage = "Ingrese un email.")]
        public string Email { get; set; }
        public string Phone { get; set; }
       
        public UserTypes TypeUser { get; set; }
        public System.DateTime InitDate { get; set; }
        public bool Active { get; set; }
        public string Address { get; set; }
        public string Locality { get; set; }
        public string District { get; set; }
        public string Latitud { get; set; }
        public string Longitud { get; set; }
        public List<Service> Services { get; set; }

    }
}