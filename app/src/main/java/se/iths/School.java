package se.iths;

public class School {
    String schoolName;
    String city;
    int schoolId;

    public School(int schoolId, String schoolName, String city) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.city = city;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
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

    @Override
    public String toString() {
        return "School{" +
                "schoolName='" + schoolName + '\'' +
                ", city='" + city + '\'' +
                ", schoolId=" + schoolId +
                '}';
    }
}
