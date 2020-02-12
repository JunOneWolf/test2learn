package cn.jof.test2020_1_15_testUnicode;

import cn.jof.utils.TimeAction;

/**
 * 解析unicode字符串
 * @author Administrator
 *
 */
public class TestParseUnicode {
	public static boolean isHex(char c) {
		return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F')
				|| (c >= 'a' && c <= 'f');
	}

	public static String parseUnicode(String data) {
		StringBuilder sb = new StringBuilder(data.length());
		for (int i = 0, size = data.length(); i < size; i++) {
			// 在长度判断与值判断后不可能空指针和转化失败。
			if (i < size - 5 && '\\' == data.charAt(i)
					&& ('u' == data.charAt(i + 1) || 'U' == data.charAt(i + 1))
					&& isHex(data.charAt(i + 2)) && isHex(data.charAt(i + 3))
					&& isHex(data.charAt(i + 4)) && isHex(data.charAt(i + 5))) {
				char a = (char) Integer.parseInt(// parseInt性能低
						String.valueOf(new char[] { data.charAt(i + 2),
								data.charAt(i + 3), data.charAt(i + 4),
								data.charAt(i + 5) }), 16);
				sb.append(a);
				i += 5;
				continue;
			}
			if (i < size - 7 && '&' == data.charAt(i)
					&& '#' == data.charAt(i + 1) && ';' == data.charAt(i + 7)
					&& ('x' == data.charAt(i + 2) || 'X' == data.charAt(i + 2))
					&& isHex(data.charAt(i + 6)) && isHex(data.charAt(i + 3))
					&& isHex(data.charAt(i + 4)) && isHex(data.charAt(i + 5))) {// 不检测是否16进制数，会被恶意数据玩死比如\\uabcg
				char a = (char) Integer.parseInt(
						String.valueOf(new char[] { data.charAt(i + 3),
								data.charAt(i + 4), data.charAt(i + 5),
								data.charAt(i + 6) }), 16);
				sb.append(a);
				i += 7;
				continue;
			}
			sb.append(data.charAt(i));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		final String hex2 = "\\u7231\\u7331\\u7431\\u7531";
		new TimeAction() {
			public void action() {
				System.out.println(parseUnicode(hex2));
			}
		}.say("简易检测时间V1");
		new TimeAction() {
			public void action() {
				System.out.println(parseUnicodeV6(hex2));
			}
		}.say("简易检测时间V2");
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < 1000000; i++) {
			s.append("\\u7231");
		}
		final String hex = s.toString();
		System.out.println("新字符串的长度：" + s.length());
		new TimeAction() {
			public void action() {
				parseUnicode(hex);
			}
		}.say("V1");
		new TimeAction() {
			public void action() {
				parseUnicodeV6(hex);
			}
		}.say("V2");
		
	}

	public static boolean isHexs(char... cs) {
		for (char d : cs) {
			if (isHex(d)) {
				return true;
			}
		}
		return false;
	}

	public static String parseUnicodeV6(String data) {
		if (data == null || data.length() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder(data.length());
		for (int i = 0, size = data.length(); i < size; i++) {
			if (i < size - 5 && '\\' == data.charAt(i)
					&& ('u' == data.charAt(i + 1) || 'U' == data.charAt(i + 1))
					&& isHexs(data.charAt(i + 2), data.charAt(i + 3),
							data.charAt(i + 4), data.charAt(i + 5))) {
				sb.append(chars2char(data.charAt(i + 2), data.charAt(i + 3),
						data.charAt(i + 4), data.charAt(i + 5)));
				i += 5;
				continue;
			}
			if (i < size - 7
					&& '&' == data.charAt(i)
					&& '#' == data.charAt(i + 1)
					&& ';' == data.charAt(i + 7)
					&& ('x' == data.charAt(i + 2) || 'X' == data.charAt(i + 2))
					&& isHexs(data.charAt(i + 3), data.charAt(i + 4),
							data.charAt(i + 5), data.charAt(i + 6))) {// 不检测是否16进制数，会被恶意数据玩死比如\\uabcg
				sb.append(chars2char(data.charAt(i + 3), data.charAt(i + 4),
						data.charAt(i + 5), data.charAt(i + 6)));
				i += 7;
				continue;
			}
			sb.append(data.charAt(i));
		}
		return sb.toString();
	}

	private static char chars2char(char a, char b, char c, char d) {
		return (char) ((Character.digit(a, 16) << 12)
				+ (Character.digit(b, 16) << 8) + (Character.digit(c, 16) << 4) + (Character
					.digit(d, 16)));
	}

}
