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

        public string id { get; set; }
        public string name { get; set; }
        public string surname { get; set; }

        [Required(ErrorMessage = "Ingrese un dni.")]
        public int dni { get; set; }
        public string password { get; set; }

        [Required(ErrorMessage = "Ingrese un email.")]
        [Key]
        public string email { get; set; }
        public string phone { get; set; }
       
        public UserTypes typeUser { get; set; }
        public System.DateTime initDate { get; set; }
        public bool active { get; set; }
        public string address { get; set; }
        public string locality { get; set; }
        public string district { get; set; }
        public string latitud { get; set; }
        public string longitud { get; set; }

    }
}