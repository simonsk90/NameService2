package Data;

import NameService.Model.NameModel;

public class NameCollection
{

    /**
     * Kollektion af data, som vi kan skyde ind i NameService.
     */
    public NameCollection()
    {
        this.nameModels = new NameModel[]
        {
            new NameModel("John", "Doe"),
            new NameModel("Hans-Christian", "Jensen"),
            new NameModel("P. H.", "Kristensen"),
            new NameModel("Peter Hans", "Kristensen"),
            new NameModel("Peter H.", "Kristensen"),

            new NameModel("Simon", "Sondrup Kristensen"),
        };
    }

    private NameModel[] nameModels;

    public NameModel[] GetNameModels()
    {
        return this.nameModels;
    };
}
