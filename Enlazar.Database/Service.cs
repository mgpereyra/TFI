using Enlazar.Database.Utilities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Enlazar.Database
{
    public class Service
    {
        public string Id { get; set; }
        public string Longitud { get; set; }
        public string Latitud { get; set; }
        public string RecolectorId { get; set; }
        public string UserId { get; set; }
        public string Address { get; set; }
        public string Date { get ; set; }
        public string Time { get ; set; }
        public int EnvasesCarton { get; set; }
        public int EnvasesPlasticos { get; set; }
        public int EnvasesVidrio { get; set; }
        public StateServices Estado { get; set; }


    }
}