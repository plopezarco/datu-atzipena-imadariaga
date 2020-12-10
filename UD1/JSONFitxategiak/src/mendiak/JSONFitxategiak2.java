/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mendiak;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;

/**
 *
 * @author izarcelaya.aritz
 */
public class JSONFitxategiak2 {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        JsonArray model = Json.createArrayBuilder()
                .add(Json.createObjectBuilder()
                        .add("Mendia", "Anboto")
                        .add("Altuera", 2368)
                        .add("Probintzia", "Bizkaia"))
                .add(Json.createObjectBuilder()
                        .add("Mendia", "Aketegi")
                        .add("Altuera", 5678)
                        .add("Probintzia", "Gipuzkoa"))
                .build();
                
                
        
        System.out.println(model);
        FileOutputStream out = new FileOutputStream("mendiakproba.json");
        JsonWriter jsonWriter = Json.createWriter(out);
        jsonWriter.writeArray(model);
        jsonWriter.close();
        out.close();
    }
}
