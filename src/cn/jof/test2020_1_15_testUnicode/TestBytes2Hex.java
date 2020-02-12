package cn.jof.test2020_1_15_testUnicode;

import java.math.BigInteger;

/**
 * 字符数组转化十六进制字符串
 * 
 */
public class TestBytes2Hex {
	/**
	 * 暂时发现性能最高，可以优化抽离为静态字符串数组
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytes2Hex_3(byte[] bytes) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		char[] resultCharArray = new char[bytes.length * 2];// 一个字节有两位十六进制的值
		int index = 0;
		for (byte b : bytes) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xF];// byte的左边四位,&转化16进制
			resultCharArray[index++] = hexDigits[b & 0xF];// byte的右边四位,&转化16进制
		}
		return new String(resultCharArray);
	}

	// 有点代码量少
	public static String bytes2Hex_1(byte[] bs) {
		String hex = new BigInteger(1, bs).toString(16);
		return hex;
	}

	public static String bytesToHex_2(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	public static String bytes2hexV1(byte[] bs) {
		StringBuilder sb = new StringBuilder(bs.length * 2);
		for (byte b : bs) {
			String tmpStr = Integer.toHexString(b & 0xFF);// 用&替换byte转化char,防止负数
			if (tmpStr.length() == 1) {
				sb.append("0");
			}
			sb.append(tmpStr);
		}
		return sb.toString().toUpperCase();// 转化大写
	}
}
