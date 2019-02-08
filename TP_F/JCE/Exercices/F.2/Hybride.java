import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Hybride
 */
public class Hybride {
    private static final byte[] clefBrute = { // 16 octets
        0, 0, 0, 0,
        0, 0, 0, 0,
        0, 0, 0, 0,
        0, 0, 0, 0 };	  
    private static Cipher chiffreur;
    // Pour manipuler le trousseau
    private static KeyStore magasin;
    private static final String nomDuTrousseau = "Exercices/F.2/MonTrousseau.jks";
    private static final char[] motDePasse = "Alain Turin".toCharArray();
    private static KeyStore.ProtectionParameter protection;

    public static ArrayList<PrivateKey> getAllPrivateKeys() throws Exception {
        //------------------------------------------------------------------
        //  Etape 1.   Charger le trousseau dans le magasin
        //------------------------------------------------------------------
        FileInputStream fis = null;
        magasin = KeyStore.getInstance("JKS");
        protection = new KeyStore.PasswordProtection(motDePasse);
        fis = new FileInputStream(nomDuTrousseau);
        magasin.load(fis, motDePasse);
        fis.close();

        //------------------------------------------------------------------
        //  Etape 2.   Lister les alias
        //------------------------------------------------------------------
        final Enumeration<String> tousLesAlias = magasin.aliases();

        //------------------------------------------------------------------
        //  Etape 3.   Récupérer les clés privées
        //------------------------------------------------------------------
        ArrayList<PrivateKey> clésPrivées = new ArrayList<PrivateKey>();

        while(tousLesAlias.hasMoreElements()) {
            String alias = tousLesAlias.nextElement();
            if(!magasin.isCertificateEntry(alias)) {
                PrivateKeyEntry entrée = (PrivateKeyEntry) magasin.getEntry(alias, protection);
                PrivateKey cléPrivée = entrée.getPrivateKey();
                if(cléPrivée.getAlgorithm().equals("RSA"))
                    clésPrivées.add(cléPrivée);
            }
        }
        return clésPrivées;
    }
    
    public static ArrayList<String> dechiffrer(String path, String filename, String encryption) throws Exception {
        ArrayList<String> clésPotentielles = new ArrayList<String>();
        ArrayList<PrivateKey> clésPrivées = getAllPrivateKeys();

        byte[] texteChiffré = Files.readAllBytes(Paths.get(path, filename));
        byte[] messageDéchiffré = null;
        try {
            chiffreur = Cipher.getInstance(encryption); 
        }
        catch (Exception e) { System.out.println(encryption + "n'est pas disponible.");}	

        for (PrivateKey cléPrivée : clésPrivées) {
            try {
                chiffreur.init(Cipher.DECRYPT_MODE, cléPrivée);
                messageDéchiffré = chiffreur.doFinal(texteChiffré);
                String strDéchiffrée = toHex(messageDéchiffré);
                int l = strDéchiffrée.length();
                if (l == 16 || l == 24 || l == 32)
                    clésPotentielles.add(strDéchiffrée);
            } catch (BadPaddingException | IllegalBlockSizeException e) {}
        }

        return clésPotentielles;
    }
    public static void main(String[] args) throws Exception {
        String path = "Exercices/F.2/";
        String filename = "clef_chiffree.pkcs1";
        ArrayList<String> clésPotentielles = dechiffrer(path, filename, "RSA/ECB/PKCS1Padding");
        for (String clé : clésPotentielles) {
            System.out.println(clé);
        }

        //Exercice F.3.2
        String[] modesOperatoires = {"CBC", "CFB", "OFB", "CTR"};
        String padding = "/PKCS5Padding";
        SecretKeySpec sks = new SecretKeySpec(clésPotentielles.get(0).getBytes(), "AES");
        //for (int i = 0; i < modesOperatoires.length; i++) {
            String modOp = modesOperatoires[3];
            dechiffreur2.dechiffrer(path + filename, path + "document_dechiffre.pdf", "AES", "/" + modOp + padding, sks);
        //}
        
    }


    public static String toHex(byte[] données) {
        StringBuffer sb = new StringBuffer();        
        for(byte k: données) {
            sb.append(String.format("%02X", k));
        }        
        return sb.toString();
    }
}