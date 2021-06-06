using Proyecto.Database;
using Firebase.Database;
using Firebase.Database.Query;
using System.Threading.Tasks;
using System;
using System.Linq;
using System.Collections.Generic;


namespace Enlazar.Servicios
{
    public class Services
    {
        private const string FirebaseDatabaseUrl = "https://enlazar-admin-default-rtdb.firebaseio.com/"; // XXXXXX should be replaced with your instance name
        private readonly FirebaseClient firebaseClient;


        public Services()
        {
            firebaseClient = new FirebaseClient(FirebaseDatabaseUrl);
        }

        public async Task AddService(Service service)
        {
            await firebaseClient
              .Child("Service")
              .PostAsync(service);
        }

        public async Task<List<KeyValuePair<string, Service>>> GetServices()
        {
            var services = await firebaseClient
              .Child("Service")
              .OnceAsync<Service>();

            return services?
              .Select(x => new KeyValuePair<string, Service>(x.Key, x.Object))
              .ToList();
        }




    }
}