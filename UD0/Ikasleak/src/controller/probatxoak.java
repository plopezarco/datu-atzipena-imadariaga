/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ikaslea;

/**
 *
 * @author IMadariaga
 */
public class probatxoak {
    public static void main(String[] args) {
        Ikaslea ik1 = new Ikaslea(1,"Idoia","Madariaga");
        
        ArrayList<Ikaslea> ikZerrenda = new ArrayList<Ikaslea>();        
        ikZerrenda.add(ik1);
        ikZerrenda.add(new Ikaslea(2,"Xabi","Raso"));
        System.out.println("ARRAYLISTA:");
        for (int i=0;i<ikZerrenda.size();i++){
            System.out.println(ikZerrenda.get(i));
        }
        
        ObservableList<Ikaslea> ikZerrendaObservablea = FXCollections.observableArrayList(new Ikaslea(3,"Beñat","Bizente"),
                new Ikaslea(4,"Aitziber","Vesga"),
                new Ikaslea(5,"Gaizka","Izagirre"));
        ikZerrendaObservablea.add(ik1);
        ikZerrendaObservablea.remove(0);
        System.out.println("OBSERVABLEARRAYLISTA:");
        for (int i=0;i<ikZerrendaObservablea.size();i++){
            System.out.println(ikZerrendaObservablea.get(i));
        }
        
        
        
        
        
    }
}
