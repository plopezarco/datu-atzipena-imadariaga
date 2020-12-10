/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvkontsola;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author izarcelaya.aritz
 */
public class Handiena {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BufferedReader br = null;
        FileReader inputStream = null;
        FileWriter outputStream = null;
        final String SEPARADOR = ";";
        
        try
        {
            br = new BufferedReader(new FileReader("C:\\Users\\izarcelaya.aritz\\Documents\\NetBeansProjects\\datu-atzipena-imadariaga\\UD1\\CsvKontsola\\src\\csvkontsola\\Mendiak.csv"));
            String linea = "";
            int i = 0;
            String mendi = "";
            while ((linea = br.readLine()) != null) {
                
                String[] campos = linea.split(SEPARADOR);
                
                   if(Integer.parseInt(campos[1]) > i)  
                    {
                        i = Integer.parseInt(campos[1]);
                        mendi = campos[0] + " " + campos[1]+ " " + campos[2];
                    } 
            }
            System.out.println("Mendia haundiena : " + mendi);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
}
