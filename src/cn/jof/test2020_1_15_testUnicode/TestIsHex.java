package cn.jof.test2020_1_15_testUnicode;

import cn.jof.utils.TimeAction;

/**
 * 检测是否是十六进制字符
 * 
 */
public class TestIsHex {
	public static boolean isHexV1(char c) {
		return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F')
				|| (c >= 'a' && c <= 'f');
	}

	public static boolean isHexV2(char c) {
		return (((c >>> 4) & 0xf) == 3 && (c & 0xf) < 10)
				|| ((((c >>> 4) & 0xf) == 4 || ((c >>> 4) & 0xf) == 6)
						&& (c & 0xf) > 0 && (c & 0xf) < 7);
	}

	public static boolean isHexV2(String str) {
		for (int i = 0, size = str.length(); i < size; i++) {
			if (!isHexV2(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isHexV1(String str) {
		for (int i = 0, size = str.length(); i < size; i++) {
			if (!isHexV1(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < 20000000; i++) {
			s.append("7231");
		}
		final String hex = s.toString();
		new TimeAction() {
			public void action() {
				System.out.println(isHexV1(hex));
			}
		}.say("V1");
		new TimeAction() {
			public void action() {
				System.out.println(isHexV2(hex));
			}
		}.say("V2");
	}
}
