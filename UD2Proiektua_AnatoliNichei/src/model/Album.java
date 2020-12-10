/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Even
 */
public class Album {

    int id;
    String izena;
    Artist artist;

    public Album() {
    }

    public Album(int id, String izena, Artist artist) {
        this.id = id;
        this.izena = izena;
        this.artist = artist;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    @Override
    public String toString() {
        return "Album{" + "id=" + id + ", izena=" + izena + ", artist=" + artist.getIzena() + '}';
    }
}
