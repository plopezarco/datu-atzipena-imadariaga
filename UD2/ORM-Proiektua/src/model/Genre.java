/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Mikel
 */
public class Genre {
    private int genreId;
    private String izena;

    public Genre() {
    }
    
    
    public Genre(int genreId, String izena) {
        this.genreId = genreId;
        this.izena = izena;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    @Override
    public String toString() {
        return "Genre{" + "genreId=" + genreId + ", izena=" + izena + '}';
    }
    
    
}
