package com.example.kolokvijumvezba;

public class Zadatak {
    private final String naziv;
    private final String vreme;

    public Zadatak(String naziv, String vreme) {
        this.naziv = naziv;
        this.vreme = vreme;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getVreme() {
        return vreme;
    }
}

