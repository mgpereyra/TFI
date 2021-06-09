using Enlazar.Database.Utilities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Enlazar.Database
{
    public class Service
    {
        public string id { get; set; }
        public string longitud { get; set; }
        public string latitud { get; set; }
        public string recolectorId { get; set; }
        public string userId { get; set; }
        public string address { get; set; }
        public string date { get ; set; }
        public string time { get ; set; }
        public int envasesCarton { get; set; }
        public int envasesPlasticos { get; set; }
        public int envasesVidrio { get; set; }
        public StateServices estado { get; set; }


    }
}