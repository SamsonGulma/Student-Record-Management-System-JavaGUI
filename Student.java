package STUDENTMANAGEMNT;

public class Student {
    private String name;
    private int rollNumber;
    private String address;
    private String contactDetails;
    public String toString() {
        return name;
    }

    public Student(String name, int rollNumber, String address, String contactDetails) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.address = address;
        this.contactDetails = contactDetails;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getContactDetails() {
        return contactDetails;
    }
}