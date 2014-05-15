package edu.fdu.raysmond.store.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Encryption helper
 * 
 * @author Raysmond
 */
public class Encryption {
	private static MessageDigest md;

	/**
	 * Encrypt string with md5 algorithm
	 */
	public static String MD5(String pass) {
		try {
			md = MessageDigest.getInstance("MD5");
			md.reset();
			byte[] passBytes = pass.getBytes();
			byte[] digested = md.digest(passBytes);
			
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < digested.length; i++) {
				sb.append(Integer.toHexString(0xff & digested[i]));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
}
