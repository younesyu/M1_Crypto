import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * dechiffreur
 */
public class dechiffreur {
    private static final byte[] clefBrute = { // 16 octets
        0, 0, 0, 0,
        0, 0, 0, 0,
        0, 0, 0, 0,
        0, 0, 0, 0 };	  
    private static Cipher chiffreur;
    private static SecretKeySpec clefSecrète;

    private static byte[] buffer = new byte[1024];
    private static int nbOctetsLus; 
    private static FileInputStream fis;
    private static FileOutputStream fos;
    private static CipherInputStream cis;

    public static void dechiffrer(String file, String decryptedFile) {
        try {
            fis = new FileInputStream(file); 
            fos = new FileOutputStream(decryptedFile);
        }
        
        catch (Exception e) { System.out.println("Fichier inexistant: "+ e.getMessage());}	
        
        try {
            chiffreur = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
        }
        catch (Exception e) { System.out.println("AES n'est pas disponible.");}	

        clefSecrète = new SecretKeySpec(clefBrute, "AES");
        byte[] iv = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        try {
            chiffreur.init(Cipher.DECRYPT_MODE, clefSecrète, ivspec);
            cis = new CipherInputStream(fis, chiffreur);
            while ( ( nbOctetsLus = cis.read(buffer) ) != -1 ) {
                fos.write(buffer, 0, nbOctetsLus);
            }
            fos.close();
            cis.close();
            fis.close();
        } catch (Exception e) { System.out.println("Déchiffrement impossible:"+ e.getMessage());}	
    }
    public static void main(String[] args) {
        dechiffrer("Exercices/F.1/mystere.pdf", "Exercices/F.1/mystere_dechiffre.pdf");

    }
}