import java.io.*;
import java.security.*;
import java.util.ArrayList;


public class cert {
    public static String md5hash(File file) {
        String hash = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            byte[] buffer = new byte[1024];
            int nbOctetsLus = fis.read(buffer);                   // Lecture du premier morceau
            while (nbOctetsLus != -1) {
                md.update(buffer, 0, nbOctetsLus); // Digestion du morceau
                nbOctetsLus = fis.read(buffer);                   // Lecture du morceau suivant
            }
            fis.close();

            byte[] resumeMD5 = md.digest();

            for(byte k: resumeMD5) {
                hash = hash.concat(String.format("%02x", k));

            }
        } catch (Exception e) { e.printStackTrace(); }

        return hash;

    }

    public static String md5String(String str) {
        File file = new File("tmp");

        try {
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter("tmp"));
            writer.write(str);
            writer.close();

        } catch (Exception e) {}
        String result = md5hash(file);

        file.delete();

        return result;

    }
    public static void main(String[] args)throws Exception 
	{
      File file = new File(args[0]); 
      
      BufferedReader br = new BufferedReader(new FileReader(file));

      String str;

      ArrayList<String> email = new ArrayList<String>();
      ArrayList<String> body = new ArrayList<String>();
      Boolean recordBody = false;
      while ((str = br.readLine()) != null) {
        System.out.println(str);
        email.add(str.toString());
        
        if(recordBody) {
            body.add(str.toString());
        }
        
        if(str.compareTo("") == 0) {
            recordBody = true;
        }
      }
      System.out.println();

      System.out.println("\n_________\n");
      System.out.println("\nContenu de l'email :\n");
      for (int i = 0; i < email.size(); i++) {
        System.out.println(email.get(i));
      }

      System.out.println("\n_________\n");
      System.out.println("\nContenu du corps :\n");
      for (int i = 0; i < body.size(); i++) {
        System.out.println(body.get(i));
      }

      System.out.println("\n_________\n");

      System.out.println("\nSqueezing body...\n");
      String bod = "";
      for (String s : body) {
          bod = bod.concat(s + "\n");
      }
      bod.substring(0, bod.length() - 2);

      System.out.println(bod);

      System.out.println("\n_________\n");
      
      System.out.println("\nhash du corps: \n" + md5String(bod));
      
      System.out.println();


      System.out.println("\n_________\n");
    }

      
      
}
