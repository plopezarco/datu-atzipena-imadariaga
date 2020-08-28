/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author IMadariaga
 */
public class Ikaslea {

    private int zenbakia;
    private String izena;
    private String abizena1;

    public int getZenbakia() {
        return zenbakia;
    }

    public void setZenbakia(int zenbakia) {
        this.zenbakia = zenbakia;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getAbizena1() {
        return abizena1;
    }

    public void setAbizena1(String abizena1) {
        this.abizena1 = abizena1;
    }

    public Ikaslea(int zenbakia, String izena, String abizena1) {
        this.zenbakia = zenbakia;
        this.izena = izena;
        this.abizena1 = abizena1;
    }

    @Override
    public String toString() {
        return "Ikaslea{" + zenbakia + ", " + izena + ", " + abizena1 + '}';
    }
}
