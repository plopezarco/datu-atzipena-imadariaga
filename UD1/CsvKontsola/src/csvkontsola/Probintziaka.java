/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvkontsola;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author izarcelaya.aritz
 */
public class Probintziaka {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BufferedReader br = null;
        FileReader inputStream = null;
        FileWriter outputStream = null;
        final String SEPARADOR = ";";
        String probintzia = "";
        ArrayList <String> probintziaArray = new ArrayList<String>();
        ArrayList <String> probintziaArray2 = new ArrayList<String>();
        
        try
        {
            br = new BufferedReader(new FileReader("C:\\Users\\izarcelaya.aritz\\Documents\\NetBeansProjects\\datu-atzipena-imadariaga\\UD1\\CsvKontsola\\src\\csvkontsola\\Mendiak.csv"));
            String linea = "";
            
            
            while ((linea = br.readLine()) != null) {
                int contador = 0;
                String[] campos = linea.split(SEPARADOR);
                probintzia = campos[2];
                probintziaArray.add(probintzia);
                for(int i = 0;i < probintziaArray.size();i++)
                {
                    if(probintziaArray.get(i).equals(probintzia))
                    {
                        
                        contador++;
                    }
                }
                if(contador == 1)
                {
                    outputStream  = new FileWriter(probintzia + ".csv");
                    probintziaArray2.add(probintzia);
                    
                }
                for(int a = 0;a < probintziaArray2.size();a++)
                {
                    if(probintziaArray2.get(a).equals(probintzia))
                    {
                        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(probintzia + ".csv", true)));
                        out.println(" Izena: " + campos[0] + " Altuera: " + campos[1]);
                        out.close();
                       
                        
                        
                        
                    }
                }
                
              
                
                
                
                outputStream.close();
               
 

                
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
}

