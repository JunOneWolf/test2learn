package cn.jof.utils;

/**
 * 工具类
 * 
 * @author JunOneWolf
 * @date 2020-1-17
 * @version 1.0
 */
public class JofUtils {
	private JofUtils() {
	}

	public static void main(String[] args) {
		String s = toUnicode("我的天0233你受惊了");
		System.out.println(bytes2hex(s.getBytes()));
		System.out.println(bytes2hex("我的天0233你受惊了".getBytes()));
	}
	/**
	 * 十六进制字符串(E68891E7)转化byte数组,
	 * @param str
	 * @return
	 */
	public static byte[] hex2bytes(String str) {
		if ((str.length() & 2) == 1) {
			return null;
		}
		byte[] bs = new byte[str.length() / 2];

		return bs;
	}

	/**
	 * byte数组转化十六进制，字符串长度为数组长度的两倍
	 * 
	 * @param bs
	 * @return
	 */
	public static String bytes2hex(byte[] bs) {
		StringBuilder sb = new StringBuilder(bs.length * 2);
		for (byte b : bs) {
			String tmpStr = Integer.toHexString(b & 0xFF);// 用&去除负数的byte
			if (tmpStr.length() == 1) {
				sb.append("0");
			}
			sb.append(tmpStr);
		}
		return sb.toString().toUpperCase();// 转化大写

	}

	/**
	 * 字符串全部转化unicode字符串,每一个都转化"\u0000"的格式
	 * 
	 * @param str
	 * @return unicode字符串
	 * @date 2020-1-17
	 */
	public static String toUnicode(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0, size = str.length(); i < size; i++) {
			sb.append(toUnicode(str.charAt(i)));
		}
		return sb.toString();
	}

	/**
	 * 字符转化unicode字符串,转化"\u0000"的格式
	 * 
	 * @param c
	 * @return String unicode字符串
	 * @date 2020-1-17
	 */
	public static String toUnicode(char c) {
		String str = Integer.toHexString(c);
		if (str.length() == 1) {
			return "\\u000".concat(str);
		} else if (str.length() == 2) {
			return "\\u00".concat(str);
		} else if (str.length() == 3) {
			return "\\u0".concat(str);
		} else {
			return "\\u".concat(str);
		}
	}

	/**
	 * 字符串是否全部为十六进制
	 * 
	 * @param str
	 * @return boolean
	 * @date 2020-1-17
	 */
	public static boolean is16Str(String str) {
		for (int i = 0, size = str.length(); i < size; i++) {
			if (!is16Char(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 字符是否符合十六进制
	 * 
	 * @param c
	 * @return 字符是否是16进制
	 * @date 2020-1-17
	 */
	public static boolean is16Char(char c) {
		return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F')
				|| (c >= 'a' && c <= 'f');
	}
}
