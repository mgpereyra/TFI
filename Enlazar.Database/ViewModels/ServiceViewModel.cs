using Enlazar.Database;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Enlazar.Database.ViewModels
{
    public class ServiceViewModel
    {
        public List<Service> ListServices { get; set; }
        public List<User> ListRecyclers { get; set; }
    }
}