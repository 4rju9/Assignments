package app.netlify.dev4rju9.netclanExplorerClone.models;

public class BusinessModel {

    String name, city, profession, image;
    int progress;

    public BusinessModel(String name, String city, String profession, String image, int progress) {
        this.name = name;
        this.city = city;
        this.profession = profession;
        this.image = image;
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getProfession() {
        return profession;
    }

    public String getImage() {
        return image;
    }

    public int getProgress() {
        return progress;
    }
}