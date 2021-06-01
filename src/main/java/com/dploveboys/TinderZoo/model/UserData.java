package com.dploveboys.TinderZoo.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name="user_data")
public class UserData {

    @Id
    private Long id;

    private String specie;
    private String breed;
    private int age;
    private String country;
    private String city;
    private int height; // in cm
    private char gender;
    private String eyeColor;
    private String description;

    private String address;

    @Transient
    private List<Long> matches_list; //this list of user_ids that matched this user should update periodically

    public UserData(){
    }


    public UserData(Long id, String specie, String breed, int age, String country, String city, int height, char gender,String eyeColor,String description, String address) {
        this.id=id;
        this.specie = specie;
        this.breed = breed;
        this.age = age;
        this.country = country;
        this.city = city;
        this.height = height;
        this.gender = gender;
        this.eyeColor=eyeColor;
        this.description=description;
        this.address = address;
    }

    public UserData(Optional<UserData> temp_user) {
        this.id=temp_user.get().id;
        this.specie = temp_user.get().specie;
        this.breed = temp_user.get().breed;
        this.age = temp_user.get().age;
        this.country = temp_user.get().country;
        this.city = temp_user.get().city;
        this.height = temp_user.get().height;
        this.gender = temp_user.get().gender;
        this.eyeColor=temp_user.get().eyeColor;
        this.description=temp_user.get().description;
        this.address = temp_user.get().address;

    }

    public void addMatch(Long another_user_id)
    {
        this.matches_list.add(another_user_id);
    }

    public void removeMatch(Long another_user_id) //needs checking
    {
        Long index = Long.valueOf(this.matches_list.indexOf(another_user_id));
        this.matches_list.remove(index);
    }

    public void clearMatches()
    {
        this.matches_list.clear();
    }

    public List<Long> getAllMatches()
    {
        return matches_list;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", specie='" + specie + '\'' +
                ", breed='" + breed + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", height=" + height +
                ", gender=" + gender +
                ", eyeColor='" + eyeColor + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserData userData = (UserData) o;

        return id == userData.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
