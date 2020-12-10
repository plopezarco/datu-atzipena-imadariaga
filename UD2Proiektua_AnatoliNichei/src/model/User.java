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
public class User {

    int id;
    String izena;
    String pasahitza;
    String rola;

    public User() {
    }

    public User(int id, String izena, String pasahitza, String rola) {
        this.id = id;
        this.izena = izena;
        this.pasahitza = pasahitza;
        this.rola = rola;
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
        return "User{" + "id=" + id + ", izena=" + izena + ", pasahitza=" + pasahitza + ", rola=" + rola + '}';
    }

    public String getPasahitza() {
        return pasahitza;
    }

    public void setPasahitza(String pasahitza) {
        this.pasahitza = pasahitza;
    }

    public String getRola() {
        return rola;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }

}
