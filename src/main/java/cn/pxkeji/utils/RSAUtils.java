package cn.pxkeji.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtils {
    private static Map<String, Object> map;

    static {
        try {
            initKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> getInsttance(){
        return map;
    }

    private static void initKey() throws Exception {
        Map<String, Object> intMap = new HashMap<>();
        File fb = new File("src/main/resources/RsaKey/public_key.dat");
        File fv = new File("src/main/resources/RsaKey/private_key.dat");
        RSAPrivateKey privateKey = null;
        RSAPublicKey publicKey = null;
        if (fb.exists() && fv.exists()) {
            FileReader pb = new FileReader(fb);
            FileReader pv = new FileReader(fv);

            BufferedReader bb = new BufferedReader(pb);
            BufferedReader bv = new BufferedReader(pv);

            String getPbKey = "";
            String getPvKey = "";

            while (true) {
                String aLine = bb.readLine();
                if (aLine == null)
                    break;
                getPbKey += aLine;
            }
            while (true) {
                String aLine = bv.readLine();
                if (aLine == null)
                    break;
                getPvKey += aLine;
            }
            BASE64Decoder b64dpb = new BASE64Decoder();
            BASE64Decoder b64dpv = new BASE64Decoder();

            byte[] keyBytepb = b64dpb.decodeBuffer(getPbKey);
            byte[] keyBytepv = b64dpv.decodeBuffer(getPvKey);

            X509EncodedKeySpec x509ekpb = new X509EncodedKeySpec(keyBytepb);
            PKCS8EncodedKeySpec x509ekpv = new PKCS8EncodedKeySpec(keyBytepv);

            KeyFactory keyFactorypb = KeyFactory.getInstance("RSA");
            KeyFactory keyFactorypv = KeyFactory.getInstance("RSA");

            publicKey = (RSAPublicKey) keyFactorypb.generatePublic(x509ekpb);
            privateKey = (RSAPrivateKey) keyFactorypv.generatePrivate(x509ekpv);
        } else {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair kp = kpg.genKeyPair();
            publicKey = (RSAPublicKey) kp.getPublic();
            privateKey = (RSAPrivateKey) kp.getPrivate();

            BASE64Encoder b64 = new BASE64Encoder();
            String pkStr = b64.encode(publicKey.getEncoded());
            String prStr = b64.encode(privateKey.getEncoded());

            FileWriter fw = new FileWriter(fv);
            fw.write(prStr);
            fw.close();
            FileWriter fw2 = new FileWriter(fb);
            fw2.write(pkStr);
            fw2.close();
        }
        intMap.put("public_key", publicKey);
        intMap.put("private_key", privateKey);
        map = intMap;
    }

    public static String encrypt(String source) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, (PublicKey) map.get("public_key"));
        byte[] sbt = source.getBytes();
        byte[] epByte = cipher.doFinal(sbt);
        BASE64Encoder encoder = new BASE64Encoder();
        String epStr = encoder.encode(epByte);
        return epStr;
    }

    public static String decrypt(String cryptograph) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, (PrivateKey) map.get("private_key"));
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b1 = decoder.decodeBuffer(cryptograph);
        byte[] b = cipher.doFinal(b1);
        return new String(b);
    }
}
