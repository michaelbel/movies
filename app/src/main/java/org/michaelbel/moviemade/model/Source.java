package org.michaelbel.moviemade.model;

public class Source {

    public String url;
    public String name;
    public String license;

    public Source(String name, String url, String license) {
        this.url = url;
        this.name = name;
        this.license = license;
    }
}