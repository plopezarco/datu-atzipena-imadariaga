/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mendiak;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 *
 * @author izarcelaya.aritz
 */
public class JSONFitxategiak {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String [] mendiak = {"Aketegi","Albertia"};
        Integer [] altuera = {1548, 868};
        String [] probintzia = {"Gipuzkoa","Araba"};
        FileOutputStream out = new FileOutputStream("Mendiak.json");
        
        for(int i = 0 ;i < 2 ;i++)
        {
                    JsonObject model = Json.createObjectBuilder()
                .add("MENDIA", mendiak[i])
                .add("ALTUERA", altuera[i])
                .add("PROBINTZIA", probintzia[i])
                .build();
                    JsonWriter jsonWriter = Json.createWriter(out);
                    jsonWriter.writeObject(model);
                   
                    
    }

        out.close();
    }
}
