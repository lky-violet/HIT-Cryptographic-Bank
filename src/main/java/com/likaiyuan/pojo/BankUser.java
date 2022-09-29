package com.likaiyuan.pojo;

public class BankUser {
    private String username;
    private String age;
    private String address;
    private String email;
    private String telephone;
    private String cardID;
    private int money;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getMoney() {
        return money;
    }

    public String getAddress() {
        return address;
    }

    public String getAge() {
        return age;
    }

    public String getCardID() {
        return cardID;
    }

    public String getTelephone() {
        return telephone;
    }

    @Override
    public String toString() {
        return "BankUser{" +
                "username='" + username + '\'' +
                ", age='" + age + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", cardID='" + cardID + '\'' +
                ", money=" + money +
                '}';
    }
}
