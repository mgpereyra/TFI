using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Enlazar.Database
{
    public class DateEnlazar
    {
        // Year gets 1999.
        public int year { get; set; }
        // Month gets 1 (January).
        public int month { get; set; }
        // Day gets 13
        public int day { get; set; }
        // Hour gets 3.
        public int hours { get; set; }
        // Minute gets 57.
        public int minutes { get; set; }
        // Second gets 32.
        public int second { get; set; }
        public string time { get; set; }
        public int timezoneOffset { get; set; }
        // Millisecond gets 11.
        public int millisecond { get; set; }
    }
}