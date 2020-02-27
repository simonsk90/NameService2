package NameService.ServiceProviderInterfaces;

import NameService.Model.NameModel;

public interface NameInterface {

    public NameModel LookupName(NameModel[] nameCollection, String input);
    public NameModel[] LookupNameRange(NameModel[] nameCollection, String input, String separator);

}
