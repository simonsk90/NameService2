package NameService.Model;

public class NameModel {

    public NameModel(String firstName, String lastName)
    {
        this.FirstName = firstName;
        this.LastName = lastName;
    }

    public String getFirstName()
    {
        return FirstName;
    }


    public String getLastName()
    {
        return LastName;
    }


    private String FirstName;

    private String LastName;

}
