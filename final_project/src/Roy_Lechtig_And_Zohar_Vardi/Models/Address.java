package Roy_Lechtig_And_Zohar_Vardi.Models;

public class Address {
    private String state;
    private String city;
    private String street;
    private int buildingNumber;

    public Address(String state, String city, String street, int buildingNumber) {
        this.state = state;
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    @Override
    public String toString() {
        return state + ", " + city + ", " + street + ", " + buildingNumber;
    }
}
