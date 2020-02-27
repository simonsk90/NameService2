package Main;


import Data.NameCollection;
import NameService.Model.NameModel;
import NameService.NameService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demo {

    /**
     * Tester resultatet af service.
     * @param args
     */
    public static void main(String[] args) {

        NameService nameService = NameService.GetInstance();
        NameCollection nameCollection = new NameCollection();

        ArrayList<NameModel> namesFound = new ArrayList<NameModel>();

        namesFound.add(lookupName(nameService, nameCollection,"John Doe"));
        namesFound.add(lookupName(nameService, nameCollection,"Doe, John"));
        namesFound.add(lookupName(nameService, nameCollection,"Hans-Christian Jensen"));
        namesFound.add(lookupName(nameService, nameCollection, "H-C Jensen"));
        namesFound.add(lookupName(nameService, nameCollection,"P. H. Kristensen"));
        namesFound.add(lookupName(nameService, nameCollection,"Kristensen, P. H."));
        namesFound.add(lookupName(nameService, nameCollection,"Peter Hans Kristensen"));
        namesFound.add(lookupName(nameService, nameCollection,"Peter H. Kristensen"));

        namesFound.addAll(Arrays.asList(lookupNameRange(nameService, nameCollection, "H-C Jensen | Peter Hans Kristensen | John Doe", "\\|")));

        //Yderligere inputs, bare lidt for sjov
//        namesFound.add(lookupName(nameService, nameCollection,"JoHn DoE"));
//        namesFound.add(lookupName(nameService, nameCollection,"JoHn DoE,"));
//        namesFound.add(lookupName(nameService, nameCollection,"JoHn, DoE,"));
//        namesFound.add(lookupName(nameService, nameCollection,"DoE, JoHn,"));
//        namesFound.add(lookupName(nameService, nameCollection,",DoE,  JoHn,"));
//        namesFound.add(lookupName(nameService, nameCollection,"Jensen, H-c"));
//        namesFound.add(lookupName(nameService, nameCollection,"Sondrup Kristensen, Simon"));
//        namesFound.add(lookupName(nameService, nameCollection,"Simon, Sondrup Kristensen")); //Kan diskuteres om det er godt eller skidt, at der findes et resultat her. Det kommer an på krav til service :-)
//        namesFound.add(lookupName(nameService, nameCollection,"Jense, H-c")); //Bør returnere null
//        namesFound.add(lookupName(nameService, nameCollection,"Jensen H-c")); //Kan diskuteres om det er godt eller skidt, at der findes et resultat her. Det kommer an på krav til service :-)


        namesFound.forEach(nameObject -> System.out.println("{ FirstName: " + (nameObject != null ? nameObject.getFirstName() : null) + " LastName: " + (nameObject != null ? nameObject.getLastName() : null) + " }"));
    }

    /**
     * Kalder service for at finde en NameModel
     * @param nameService Service som vil levere resultatet
     * @param nameCollection Liste af objekter, som vi vil lede i.
     * @param nameInput String som bruges til at søge i nameCollection efter et objekt.
     * @return fundet NameModel
     */
    public static NameModel lookupName(NameService nameService, NameCollection nameCollection, String nameInput)
    {
        return nameService.LookupName(nameCollection.GetNameModels(), nameInput);
    }

    /**
     * Kalder service for at finde et array af NameModel
     * @param nameService Service som vil levere resultatet
     * @param nameCollection Liste af objekter, som vi vil lede i.
     * @param nameInput String som bruges til at søge i nameCollection efter et objekt.
     * @param separator Separator som er brugt til at separere navne i nameInput
     * @return fundne nameModels
     */
    public static NameModel[] lookupNameRange(NameService nameService, NameCollection nameCollection, String nameInput, String separator)
    {
        return nameService.LookupNameRange(nameCollection.GetNameModels(), nameInput, separator);
    }

}
