using Enlazar.Database;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Enlazar.Servicios
{
    public class RecyclerService
    {

        public void ActualizarCampos(User anterior, User recycler)
        {
            anterior.Address = recycler.Address;
            anterior.District = recycler.District;
            anterior.Dni = recycler.Dni;
            anterior.Email = recycler.Email;
            anterior.Name = recycler.Name;
            anterior.Surname = recycler.Surname;
            anterior.Phone = recycler.Phone;
            anterior.Locality = recycler.Locality;

        }

    }
}