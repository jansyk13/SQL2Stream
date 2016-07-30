package edu.jsykora.sql2stream.models;

public class TestingModel {
    private String name;
    private int number;

    public TestingModel() {
    }

    public TestingModel(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return this.name + "-" + this.number;
    }
}
