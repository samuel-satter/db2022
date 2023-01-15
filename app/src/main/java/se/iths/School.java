package se.iths;

public class School {
    String schoolName;
    String city;

    public School(String schoolName, String city) {
        this.schoolName = schoolName;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
