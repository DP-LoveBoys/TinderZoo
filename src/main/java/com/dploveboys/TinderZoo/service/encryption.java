package com.dploveboys.TinderZoo.service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class encryption {

    private static final String k = "Bar12345Bar12345";
    private Cipher ecipher;
    private Cipher dcipher;

    public encryption()  {
        SecretKey key = new SecretKeySpec(k.getBytes(), "AES");
        try {
            ecipher = Cipher.getInstance("AES");
            dcipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }


    }

    public String encrypt(String str) {
        // Encode the string into bytes using utf-8
        byte[] utf8;
        utf8 = str.getBytes(StandardCharsets.UTF_8);

        // Encrypt
        byte[] enc = new byte[0];
        try {
            enc = ecipher.doFinal(utf8);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        // Encode bytes to base64 to get a string
        Base64.Encoder e = Base64.getEncoder();
        return new String(e.encode(enc));
    }

    public String decrypt(String str)  {
        // Decode base64 to get bytes
        Base64.Decoder e = Base64.getDecoder();
        byte[] dec = e.decode(str);
        byte[] utf8 = new byte[0];
        try {
            utf8 = dcipher.doFinal(dec);
        } catch (IllegalBlockSizeException | BadPaddingException illegalBlockSizeException) {
            illegalBlockSizeException.printStackTrace();
        }

        // Decode using utf-8
        return new String(utf8, StandardCharsets.UTF_8);
    }
}