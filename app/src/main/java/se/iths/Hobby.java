package se.iths;

public class Hobby {

    int hobbyId;
    String hobbyName;


    public Hobby(int hobbyId, String hobbyName) {
        this.hobbyId = hobbyId;
        this.hobbyName = hobbyName;
    }

    public String getHobbyName() {
        return hobbyName;
    }

    public int getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(int hobbyId) {
        this.hobbyId = hobbyId;
    }

    public void setHobbyName(String hobbyName) {
        this.hobbyName = hobbyName;
    }

    @Override
    public String toString() {
        return "Hobby{" +
                "hobbyId=" + hobbyId +
                ", hobbyName='" + hobbyName + '\'' +
                '}';
    }
}
