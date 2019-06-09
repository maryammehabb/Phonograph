package com.example.android.connection;

public class User {
    private String mail;
    private String ID;
    private String address;
    private  String phone;
    private String password;
    private String discriminator;
    private String name;



    public User(String mail, String ID, String address, String phone, String password, String discriminator, String name) {
        this.mail = mail;
        this.ID = ID;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.discriminator = discriminator;
        this.name = name;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public void setName(String name) {
        this.name = name;

    }


    public User() {
    }

    public User(String mail, String ID) {
        this.mail = mail;
        this.ID = ID;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
