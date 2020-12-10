package csvkontsola;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ordenatu {

    public static void main(String[] args) {

        BufferedReader br = null;
        FileReader inputStream = null;
        FileWriter outputStream = null;
        final String SEPARADOR = ";";
        String linea;
        ArrayList<Mendia> mendiak = new ArrayList<Mendia>();
     

        try {
            br = new BufferedReader(new FileReader("C:\\Users\\izarcelaya.aritz\\Documents\\NetBeansProjects\\datu-atzipena-imadariaga\\UD1\\CsvKontsola\\src\\csvkontsola\\Mendiak.csv"));
          
            
            while ((linea = br.readLine()) != null) {
                
                 String[] campos = linea.split(SEPARADOR);
                 mendiak.add(new Mendia(campos[0], campos[1], campos[2]));
               
            }
            

            
//            for(int i = 0; i < mendiak.size() ;i++)
//            {
//                for(int a = 0;  a < mendiak.size(); a++)
//                {
//                    if(Integer.parseInt(mendiak.get(a).getAltuera()) > Integer.parseInt(mendiak.get(i).getAltuera()))
//                    {
//                        mendiak.add(i, mendiak.get(a));
//                        
//                        
//                        
//                        
//                    }
//                }
//            }
//            
//            for(int i = 0; i < mendiak.size() ;i++)
//            {
//                System.out.println(mendiak.get(i));
//            }
            mendiak.sort();
            System.out.println(mendiak);
            


            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ordenatu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ordenatu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
