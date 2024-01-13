package com.example.medicalcard;

public class MedicalCard {

    private long id;
    private String name;
    private String emergencyContact;
    private String address;
    private String gender;
    private String bloodGroup;

    public MedicalCard() {
        // Empty constructor required by some frameworks
    }

    public MedicalCard(String name, String age, String dob, String emergencyContact, String address, String gender, String bloodGroup) {
        this.name = name;
        this.emergencyContact = emergencyContact;
        this.address = address;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
    }

    public MedicalCard(long id, String name, String emergencyContact, String address, String gender, String bloodGroup) {
        this.id = id;
        this.name = name;
        this.emergencyContact = emergencyContact;
        this.address = address;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}


