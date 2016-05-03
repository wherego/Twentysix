package miuyongjun.twentysix.utils;

import android.text.TextUtils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
public class EncryptUtils {
	private final static String DECRYPT_KEY = "@GUIZHI";
	private final static String HEX = "0123456789ABCDEF";
	private final static boolean IsNeedDecrypt = true;// 是否需要解密
	private static String str_salt;
	private static byte[] bytes_salt;
	private static EncryptUtils encryption;

	public synchronized static EncryptUtils getInstance() {
		if (encryption == null) {
			encryption = new EncryptUtils();
		}
		return encryption;
	}
	/*
	 * 生成随机数，可以当做动态的密钥 加密和解密的密钥必须一致，不然将不能解密
	 */
	public static String generateSalt() {
		try {
			SecureRandom localSecureRandom = SecureRandom
					.getInstance("SHA1PRNG");
			bytes_salt = new byte[20];
			localSecureRandom.nextBytes(bytes_salt);
			str_salt = toHex(bytes_salt);
			return str_salt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 加密
	 */
	public static String encrypt(String cleartext) {
		if (!IsNeedDecrypt) {
			return cleartext;
		}
		if (TextUtils.isEmpty(cleartext)) {
			return cleartext;
		}
		try {
			byte[] result = encrypt(cleartext.getBytes());
			return toHex(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 解密
	 */
	public static String decrypt(String encrypted) {
		if (!IsNeedDecrypt) {
			return encrypted;
		}
		if (TextUtils.isEmpty(encrypted)) {
			return encrypted;
		}
		try {
			byte[] enc = toByte(encrypted);
			byte[] result = decrypt(enc);
			return new String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
		sr.setSeed(seed);
		kgen.init(128, sr); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	private static byte[] encrypt(byte[] clear) throws Exception {
		byte[] raw = getRawKey(DECRYPT_KEY.getBytes());
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] encrypted) throws Exception {
		byte[] raw = getRawKey(DECRYPT_KEY.getBytes());
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}

	public static String fromHex(String hex) {
		return new String(toByte(hex));
	}

	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
					16).byteValue();
		return result;
	}

	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}
}
