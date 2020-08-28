/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author root
 */
public class Memoria {
    
    public static ObservableList<Ikaslea> zerrendaSortu(){
        return FXCollections.observableArrayList(
                new Ikaslea(1,"Jokin","Iribar"),
                new Ikaslea(3, "Beñat", "Bizente"),
                new Ikaslea(4, "Aitziber", "Vesga"),
                new Ikaslea(5, "Gaizka", "Izagirre"));            
    }
}
