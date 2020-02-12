package cn.jof.test2020_1_15_testUnicode;

/**
 * 十六进制转化字节数组
 *
 */
public class TestHex2Bytes {

	/**
	 * 十六进制字符串(E68891E7)转化byte数组,
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hex2bytes(String hex) {
		if (hex == null || hex.length() == 0 || (hex.length() & 2) == 1) {
			return null;
		}
		byte[] bs = new byte[hex.length() / 2];
		for (int i = 0, size = hex.length(); i < size; i += 2) {
			bs[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + (Character
					.digit(hex.charAt(i + 1), 16)));
		}
		return bs;
	}

	// 网上
	public static byte[] hexToByteArray(String hex) {// 性能低，subString,parseInt。问题：数字格式化异常,空指针。
		byte[] result = new byte[hex.length() / 2];
		for (int len = hex.length(), index = 0; index <= len - 1; index += 2) {
			String subString = hex.substring(index, index + 2);
			int intValue = Integer.parseInt(subString, 16);
			result[index / 2] = (byte) intValue;
		}
		return result;
	}

}
