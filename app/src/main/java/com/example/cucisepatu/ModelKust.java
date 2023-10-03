package com.example.cucisepatu;

public class ModelKust {
    private int id;
    private String nama;
    private int usia;
    private boolean isActive;

    //cunstractors

    public ModelKust(int id, String nama, int usia, boolean isActive) {
        this.id = id;
        this.nama = nama;
        this.usia = usia;
        this.isActive = isActive;
    }

    public ModelKust() {

    }
    //tostring is necessary for printing the contents of class object

    @Override
    public String toString() {
        return "ID: " + id +
                ", Nama: " + nama +
                ", Usia: " + usia;
    }


    //getset

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getUsia() {
        return usia;
    }

    public void setUsia(int usia) {
        this.usia = usia;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
