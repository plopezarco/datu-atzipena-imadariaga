/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonfitxategiak;

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
public class zerotikSortu {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        JsonObject model = Json.createObjectBuilder()
                .add("firstName", "Duke")
                .add("lastName", "Java")
                .add("age", 18)
                .add("streetAddress", "100 Internet Dr")
                .add("city", "JavaTown")
                .add("state", "JA")
                .add("postalCode", "12345")
                .add("phoneNumbers", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("type", "mobile")
                                .add("number", "111-111-1111"))
                        .add(Json.createObjectBuilder()
                                .add("type", "home")
                                .add("number", "222-222-2222")))
                .build();

        System.out.println(model);
        FileOutputStream out = new FileOutputStream("datuberriak.json");
        JsonWriter jsonWriter = Json.createWriter(out);
        jsonWriter.writeObject(model);
        jsonWriter.close();
        out.close();
    }
}
