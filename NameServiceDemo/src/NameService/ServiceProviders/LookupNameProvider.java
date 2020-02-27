package NameService.ServiceProviders;

import Data.NameCollection;
import NameService.Model.NameModel;
import NameService.ServiceProviderInterfaces.NameInterface;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.stream.Stream;

public class LookupNameProvider implements NameInterface
{

    /**
     * Søger i en kollektion af NameModel, for at finde et objekt med efterspurgt for- og efternavn
     * @param nameCollection Liste af objekter, som vi vil lede i.
     * @param input String som bruges til at søge i nameCollection efter et objekt.
     * @return Fundet navn i kollektion, ud fra input
     */
    public NameModel LookupName(NameModel[] nameCollection, String input)
    {
        NameModel result = null;
        Iterator<NameModel> nameCollectionIterator = Arrays.stream(nameCollection).iterator(); //Lav en iterator for alle navnene fra data-laget
        String contractedFirstNamePattern = "\\b([a-z])-([a-z]){1}\\b"; //Regex pattern, til at vurdere om input f.eks. == 'h-c'
        String[] inputParts = SplitInputToParts(input); //Split pr. 'mellemrum' i input

        //Loop igennem alle navne, indtil resultatet er fundet.
        while (result == null && nameCollectionIterator.hasNext())
        {
            NameModel currentNameModel = nameCollectionIterator.next();

            //lowerCase af for- og efternavn, fordi vi har bedre chance for at finde et match, hvis alt vi matcher er lower case.
            String currentNameModelFirstName = currentNameModel.getFirstName().toLowerCase();
            String currentNameModelLastName = currentNameModel.getLastName().toLowerCase();

            //Er der en af inputParts, som matcher currentNameModel.FirstName?
            //Checker først om der er et direkte match, hvis ikke, så se om det er fordi at inputPart er en sammentrukket string, og om der kan findes et match på den måde.
            Optional<String> firstName = Arrays.stream(inputParts)
                .filter(inputPart -> inputPart.equals(currentNameModelFirstName) ||
                    (
                        inputPart.matches(contractedFirstNamePattern) && currentNameModelFirstName.contains("-") &&
                        inputPart.equals(GetContractionOfString(currentNameModelFirstName, "-"))
                    )
                )
                .findFirst();

            //Er der en af inputParts, som matcher currentNameModel.LastName?
            Optional<String> lastName = Arrays.stream(inputParts)
                .filter(inputPart -> { return inputPart.toLowerCase().equals(currentNameModelLastName); })
                .findFirst();

            //Hvis der er et match på både firstName og lastName, så er der fundet et resultat.
            if (firstName.isPresent() && lastName.isPresent())
            {
                result = currentNameModel;
            }
        }

        return result;
    }

    /**
     * Søger i en kollektion af NameModel, for at finde et eller flere objekter med efterspurgte for- og efternavne
     * @param nameCollection Liste af objekter, som vi vil lede i.
     * @param input String som bruges til at søge i nameCollection efter objekter.
     * @param separator Separator som er brugt til at separere navne i nameInput
     * @return Array med fundne NameModel
     */
    public NameModel[] LookupNameRange(NameModel[] nameCollection, String input, String separator)
    {
        NameModel[] result;
        ArrayList<NameModel> nameModelList = new ArrayList<NameModel>() ;
        String[] splittedInput = input.split(separator); //Split input op pr separator

        //Kald LookupName pr. splittedInput
        for (String inputPart : splittedInput)
        {
            NameModel oneNameModel = LookupName(nameCollection, inputPart.trim());
            if (oneNameModel != null)
            {
                nameModelList.add(oneNameModel);
            }
        }

        result = (NameModel[]) nameModelList.toArray(new NameModel[nameModelList.size()]);

        return result;
    }

    /**
     * Split en string til et array, med 'mellemrum' som separator. Sørger for at efternavn altid er sidste element i array. Alle elementer kommer ud som lowercase
     * @param input
     * @return input splittet til array
     */
    private String[] SplitInputToParts(String input)
    {
        String[] inputParts = input
                .toLowerCase()
                .split(" ");

        //Hvis der er mere end 2 ord i input
        if (inputParts.length == 3)
        {
            //Find mulig string som indeholder komma
            Optional<String> inputPartWithComma = Arrays.stream(inputParts)
                    .filter(x -> x.contains(","))
                    .findFirst();

            //Hvis der er en part som indeholder komma, så sørg for at den samt alle elementer FØR den, bliver concatenated til én part
            if (inputPartWithComma.isPresent())
            {
                int indexOfInputPartWithComma = Arrays.asList(inputParts).indexOf(inputPartWithComma.get());
                String[] thoseBeforeComma = new String[indexOfInputPartWithComma + 1];
                String[] thoseAfterComma = new String[inputParts.length -thoseBeforeComma.length];

                for (int i = 0; i < indexOfInputPartWithComma + 1; i++)
                {
                    thoseBeforeComma[i] = inputParts[i];
                }

                for (int i = 0; i < inputParts.length - thoseBeforeComma.length; i++)
                {
                    thoseAfterComma[i] = inputParts[thoseBeforeComma.length + i];
                }

                String thoseBeforeCommaAsOneString = String.join(" ", thoseBeforeComma);
                String thoseAfterCommaAsOneString = String.join(" ", thoseAfterComma);

                inputParts = new String[] {
                    thoseBeforeCommaAsOneString,
                    thoseAfterCommaAsOneString
                };

            }
            // Antag at det er to fornavne, og et efternavn
            else
            {
                inputParts = new String[]{inputParts[0] + " " + inputParts[1], inputParts[2]};
            }
        }

        //Fjern alle kommaer i array, de er unødvendige nu hvor vi er sikre på at efternavn befinder sig sidst i array, hvis der er et komma.
        inputParts = Arrays
            .stream(inputParts)
            .map(x -> x.replace(",", ""))
            .toArray(String[]::new);

        return inputParts;
    }

    /**
     * @description Sammentræk en String som indeholder "-", med første bogstav fra hver 'del' af ordet. F.eks. hans-christian -> h-c
     * @param stringToContract
     * @param separator
     * @return Sammentrukket string
     */
    private String GetContractionOfString(String stringToContract, String separator)
    {
        String result = String.join(separator, Arrays.stream(stringToContract.split("-")).map(w -> String.valueOf(w.charAt(0))).toArray(String[]::new));
        return result;
    }

}
