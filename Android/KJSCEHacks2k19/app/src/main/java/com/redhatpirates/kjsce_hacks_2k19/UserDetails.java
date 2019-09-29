package com.redhatpirates.kjsce_hacks_2k19;

public class UserDetails {
    String name,age,email,password,phone;

    public UserDetails(String name, String age, String email, String password, String phone) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }
}
