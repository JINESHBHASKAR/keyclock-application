package com.nmsecurity.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptDecrypt {
	private final static Logger log = LoggerFactory.getLogger(EncryptDecrypt.class);
	
	private static SecretKeySpec secretKey;
    private static byte[] key;
	
	public static void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			String s = new String(key);
			System.out.println("Key :"+s);
	        sha = MessageDigest.getInstance("SHA-1");	        
	        key = sha.digest(key);
	        System.out.println("Key :"+new String(key));
	        key = Arrays.copyOf(key, 16); 
	        System.out.println("Key :"+new String(key));
	        secretKey = new SecretKeySpec(key, "AES");
		}catch(UnsupportedEncodingException | NoSuchAlgorithmException e) {
			log.error("error during setting key: ",e.getMessage());
		}
		
	}
	
	public static String encrypt(String strToEncrypt, String secret) {
		String encrypted = null;
		setKey(secret);
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			encrypted = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			log.error("error during encrypt : ",e.getMessage());
		}
		
		return encrypted;
	}
	
	public static String decrypt(String strToDecrypt, String secret) {
		String decrypted = null;
		setKey(secret);
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			decrypted = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {			
			log.error("error during decrypt : ",e.getMessage());
		}
		
		return decrypted;		
	}
	
	/*public static void main(String[] args) {
		String secretKey = "EQR8RGzOIbwfkaKq7WLqbw==";
		String originalString = "STAF001";
		String encryptedString = EncryptDecrypt.encrypt(originalString, secretKey);
		String decryptedString = EncryptDecrypt.decrypt(encryptedString, secretKey);
		System.out.println(originalString);
		System.out.println(encryptedString);
		System.out.println(decryptedString);

	}*/
	 
}
