/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mendiak;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonWriter;

/**
 *
 * @author izarcelaya.aritz
 */
public class MendiakGehitu {
    public static void main(String[] args) throws FileNotFoundException, IOException {

        JsonReader reader = Json.createReader(new FileReader("Mendiak2.json"));
        JsonStructure jsonst = reader.read();

        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("Mendia", "Gorbea").
                add("Altuera", 3500).
                add("Probintzia", "Gipuzkoa");
        JsonObject ob = job.build();

        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (int i = 0; i < jsonst.asJsonArray().size(); i++) {
            jab.add(jsonst.asJsonArray().get(i));
        }
        jab.add(ob);

        JsonArray ar = jab.build();

        FileOutputStream out = new FileOutputStream("Mendiak3.json");
        JsonWriter jsonWriter = Json.createWriter(out);
        jsonWriter.writeArray(ar);
        jsonWriter.close();
        out.close();

    }

}