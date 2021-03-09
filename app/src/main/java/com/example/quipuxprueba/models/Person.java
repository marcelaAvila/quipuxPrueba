package com.example.quipuxprueba.models;

import com.example.quipuxprueba.app.MyApplication;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Person extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String typeId;
    private String numberId;
    @Required
    private String name;
    @Required
    private String lastName;
    private int age;
    @Required
    private String gender;
    @Required
    private Date createAt;

    public Person() {
    }

    public Person(String typeId,String numberId,String name,String lastName,int age,String gender) {
        this.id = MyApplication.PersonID.incrementAndGet();
        this.typeId = typeId;
        this.numberId = numberId;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.createAt = new Date();
    }

    public int getId() {
        return id;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getNumberId() {
        return numberId;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setNumberId(String numberId) {
        this.numberId = numberId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
