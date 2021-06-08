using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Enlazar.Database.Utilities
{
    public enum UserTypes
    {
        //vecinos
        USER = 1,
        //admin de la cooperativa
        ADMIN = 3,
        //recolectores
        RECYCLER = 2
    }
    public enum StateServices
    {
        //vecinos
        PENDIENTE = 0,
        //admin de la cooperativa
        ASIGNADO = 1,
        //recolectores
        FINALIZADO = 2
    }
}