package com.AD340.Roommater;

public class ProfileData {
    private String name;
    private String age;
    private String zip;

    public ProfileData(String name, String age, String zip) {
        this.name = name;
        this.age = age;
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }


}
