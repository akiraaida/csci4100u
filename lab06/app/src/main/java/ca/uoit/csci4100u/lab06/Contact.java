package ca.uoit.csci4100u.lab06;

/**
 * Created by akira on 10/16/17.
 */

public class Contact {

    private int id;
    private String firstName;
    private String lastName;
    private String phone;

    public Contact(int id, String firstName, String lastName, String phone) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + "," + firstName + "," + lastName + "," + phone;
    }
}
