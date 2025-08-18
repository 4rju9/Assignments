package app.netlify.dev4rju9.netclanExplorerClone.models;

public class PersonalModel {
    String name, city, distance, purpose, image;
    int progress;

    public PersonalModel(String name, String city, String distance, String purpose, String image, int progress) {
        this.name = name;
        this.city = city;
        this.distance = distance;
        this.purpose = purpose;
        this.image = image;
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getDistance() {
        return distance;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getImage() {
        return image;
    }

    public int getProgress() {
        return progress;
    }
}