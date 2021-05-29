using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Enlazar_AdminMVC.Extensions
{
   

    [MetadataType(typeof(RecyclerMetadata))]
    public partial class Recycler
    {
    }
    public class RecyclerMetadata
    {
        // Apply RequiredAttribute
        [Required(ErrorMessage = "El nombre es requerido.")]
        public string Name;
    }
}