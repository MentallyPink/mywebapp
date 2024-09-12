package org.project.Utilities;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class StringUtilities {

	private static final String SECRET_KEY = "1234567890123456";

	/**
	 * Controlla che la stringa in parametro sia nulla o vuota
	 * 
	 * @param String stringa che vuoi controllare
	 * @return true se la stringa e' nulla o vuota, altrimenti false
	 */
	public static boolean isEmpty(String stringa) {
		if (stringa == null || stringa.isEmpty())
			return true;
		return false;
	}

	public static String encryptString(String string) {
		try {

			SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			byte[] encryptedBytes = cipher.doFinal(string.getBytes());
			return Base64.getEncoder().encodeToString(encryptedBytes);

		} catch (Exception e) {
			return null;
		}

	}

	public static String decryptString(String string) throws Exception {
		SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		byte[] decodedBytes = Base64.getDecoder().decode(string);
		byte[] decryptedBytes = cipher.doFinal(decodedBytes);

		return new String(decryptedBytes);
	}

}
