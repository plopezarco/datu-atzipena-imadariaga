/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvkontsola;

/**
 *
 * @author izarcelaya.aritz
 */
public class Mendia {

    private String izena;
    private String altuera;
    private String probintzia;

    public Mendia() {

    }
    
     public Mendia(String izena, String altuera, String probintzia) {
         this.izena = izena;
         this.altuera = altuera;
         this.probintzia = probintzia;
    }

    public String getIzena() {
        return izena;
    }

    public String getAltuera() {
        return altuera;
    }

    public String getProbintzia() {
        return probintzia;
    }
    
    @Override
    public String toString() {
        return "[Izena=" + izena + ", Altuera=" + altuera + ", Probintzia=" + probintzia + "]";
    }
}
