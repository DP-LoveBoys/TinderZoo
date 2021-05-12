package com.dploveboys.TinderZoo.service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;

class Encryption {

    Cipher ecipher;
    Cipher dcipher;

    Encryption(SecretKey key) throws Exception {
        ecipher = Cipher.getInstance("AES");
        dcipher = Cipher.getInstance("AES");
        ecipher.init(Cipher.ENCRYPT_MODE, key);
        dcipher.init(Cipher.DECRYPT_MODE, key);
    }

    public String encrypt(String str) throws Exception {
        // Encode the string into bytes using utf-8
        byte[] utf8 = str.getBytes("UTF8");

        // Encrypt
        byte[] enc = ecipher.doFinal(utf8);

        // Encode bytes to base64 to get a string
        Base64.Encoder e = Base64.getEncoder();
        return new String(e.encode(enc));
    }

    public String decrypt(String str) throws Exception {
        // Decode base64 to get bytes
        Base64.Decoder e = Base64.getDecoder();
        byte[] dec = e.decode(str);
        byte[] utf8 = dcipher.doFinal(dec);

        // Decode using utf-8
        return new String(utf8, "UTF8");
    }
}