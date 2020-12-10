
public class Batuketa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int a = 0;
        int b = 0;
        
        try
        {
            a = Integer.parseInt(args[0]);
            b = Integer.parseInt(args[1]);
            
        }catch(NumberFormatException e)
        {
            e.printStackTrace();
        }
        int batura = a + b ;
        
        System.out.println("Batura: " + a + "+" + b + " = " + batura);
    }
    
}
