package NameService;

import NameService.Model.NameModel;
import NameService.ServiceProviderInterfaces.NameInterface;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class NameService {

    private static NameService service;
    private ServiceLoader<NameInterface> serviceLoader;

    private NameService()
    {
        serviceLoader = ServiceLoader.load(NameInterface.class);
    }

    /**
     * Singleton implementation
     * @return NameService
     */
    public static synchronized NameService GetInstance()
    {
        if (service == null)
        {
            service = new NameService();
        }
        return service;
    }

    /**
     * Søger i en kollektion af NameModel, for at finde et objekt med efterspurgt for- og efternavn
     * @param nameCollection Liste af objekter, som vi vil lede i.
     * @param input String som bruges til at søge i nameCollection efter et objekt.
     * @return fundet NameModel
     */
    public NameModel LookupName(NameModel[] nameCollection, String input)
    {
        NameModel result = null;
        try
        {
            //Vi har kun én serviceProvider, og dermed virker denne serviceLoader måske lidt ligegyldig,
            //men denne serviceLoader gør det muligt at extende vores NameService i fremtiden.
            Iterator<NameInterface> serviceProviders = serviceLoader.iterator();
            while (result == null && serviceProviders.hasNext())
            {
                NameInterface serviceProvider = serviceProviders.next();
                result = serviceProvider.LookupName(nameCollection, input);
            }
        }
        catch(ServiceConfigurationError err)
        {
            result = null;
            err.printStackTrace();
        }
        return result;
    }

    /**
     * Søger i en kollektion af NameModel, for at finde et eller flere objekter med efterspurgte for- og efternavne
     * @param nameCollection Liste af objekter, som vi vil lede i.
     * @param input String som bruges til at søge i nameCollection efter objekter.
     * @param separator Defineret separator for at separere navne i input string.
     * @return Array med fundne NameModels
     */
    public NameModel[] LookupNameRange(NameModel[] nameCollection, String input, String separator)
    {
        NameModel[] result = null;
        try
        {
            //Vi har kun én serviceProvider, og dermed virker denne serviceLoader måske lidt ligegyldig,
            //men denne serviceLoader gør det muligt at extende vores NameService i fremtiden.
            Iterator<NameInterface> serviceProviders = serviceLoader.iterator();
            while (result == null && serviceProviders.hasNext())
            {
                NameInterface serviceProvider = serviceProviders.next();
                result = serviceProvider.LookupNameRange(nameCollection, input, separator);
            }
        }
        catch(ServiceConfigurationError err)
        {
            result = null;
            err.printStackTrace();
        }
        return result;
    }

}
