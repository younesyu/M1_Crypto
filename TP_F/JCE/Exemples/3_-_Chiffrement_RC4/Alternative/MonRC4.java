import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.CipherOutputStream;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class MonRC4
{   
    public static void main(String[] args) throws Exception
    {
        FileInputStream fis = new FileInputStream("butokuden.jpg");
        byte[] buffer = new byte[1024];
        int nbOctetsLus; 
        FileOutputStream fos =  new FileOutputStream("confidentiel.jpg");

        byte[] clefBrute = new byte[] { // C'est "KYOTO"
            (byte) 0x4B, (byte) 0x59, (byte) 0x4F, (byte) 0x54, (byte) 0x4F }; 

        Cipher chiffreur = Cipher.getInstance("RC4");
        SecretKeySpec clefSecrète = new SecretKeySpec(clefBrute, "RC4");
        chiffreur.init(Cipher.ENCRYPT_MODE, clefSecrète);

        CipherOutputStream cos = new CipherOutputStream(fos, chiffreur);
        
        while ( ( nbOctetsLus = fis.read(buffer) ) != -1 ) {
            cos.write(buffer, 0, nbOctetsLus);
        }
    }
}

/*
  $ make
  ...
  Lancez "java -cp ./:./bcprov-jdk15on-153.jar MonRC4"
  $ java -cp ./:./bcprov-jdk15on-153.jar MonRC4
  $ md5 confidentiel.jpg 
  MD5 (confidentiel.jpg) = d5883b49aedf986eae2396b2e0617bc7
  $ 
*/
