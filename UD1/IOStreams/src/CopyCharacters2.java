import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CopyCharacters2 {
    public static void main(String[] args) throws IOException {

        FileReader inputStream = null;
        FileWriter outputStream = null;

        try {
            inputStream = new FileReader("xanadu.txt");
            outputStream = new FileWriter("characteroutput2.txt");

            int c;
            int z = args[0].charAt(0);
            int x = args[1].charAt(0);
            while ((c = inputStream.read()) != -1) {
                if(c == z){
                    c = x;
                }
                outputStream.write(c);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}