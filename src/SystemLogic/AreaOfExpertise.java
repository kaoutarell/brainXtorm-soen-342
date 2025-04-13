package SystemLogic;

public class AreaOfExpertise {
    private String name;
    private String description;

    public AreaOfExpertise(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name + ":" + description;
    }

    public static AreaOfExpertise fromString(String str) {
        String[] parts = str.split(":");
        return new AreaOfExpertise(parts[0], parts.length > 1 ? parts[1] : "");
    }
}
