package miuyongjun.twentysix.utils;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 *Md5加密工具类
 * 
 */
public class MD5Utils {

	public static String md5(String strMd5) {
		if (TextUtils.isEmpty(strMd5)) {
			return null;
		}
		String sRet = null;
		try {
			MessageDigest alga = MessageDigest
					.getInstance("MD5");
			alga.update(strMd5.getBytes());
			byte[] digesta = alga.digest();
			sRet = byte2hex(digesta);
		} catch (NoSuchAlgorithmException ex) {
		}
		return sRet;
	}

	public static String byte2hex(byte[] b) // 二行制转字符串
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + "";
		}
		return hs;
	}
	
	
    /**
    *
    * @param plainText
    *            明文
    * @return 32位密文
    */
   public static String encryption(String plainText) {
       String re_md5 = new String();
       try {
           MessageDigest md = MessageDigest.getInstance("MD5");
           md.update(plainText.getBytes());
           byte b[] = md.digest();

           int i;

           StringBuffer buf = new StringBuffer("");
           for (int offset = 0; offset < b.length; offset++) {
               i = b[offset];
               if (i < 0)
                   i += 256;
               if (i < 16)
                   buf.append("0");
               buf.append(Integer.toHexString(i));
           }

           re_md5 = buf.toString();

       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       }
       return re_md5;
   }
}
