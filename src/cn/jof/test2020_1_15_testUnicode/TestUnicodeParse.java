package cn.jof.test2020_1_15_testUnicode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.jof.utils.TimeAction;

/**
 * <pre>
 * 解析普通字符串并且替换其中所有的unicode字符。其中1.txt放在当前java项目下。
 * 可以只保留这两个个方法。
 * parseUnicodeV4,性能最高，但不怎么友好，可以重命名。
 * check16Number, * toUnicode
 * 一次测试结果：
 *  文件的内容长度：6000000
 * V1花费时间(毫秒)：546
 * V2花费时间(毫秒)：437
 * V3花费时间(毫秒)：422
 * V4花费时间(毫秒)：63
 * </pre>
 * 
 * @author JunOneWolf
 * @time 2020-1-15
 */
public class TestUnicodeParse {
	

	/**
	 * 替换掉数据中的unicode数据如&#x7231;数据或是\u7231(包括大小写)
	 * 
	 * @param data
	 * @return
	 */
	public static String parseUnicodeV1(String data) {
		StringBuilder sb = new StringBuilder(data.length());
		String tmpData = "";
		for (int i = 0, size = data.length(); i < size; i++) {
			// tmpData 在长度判断后不可能空指针。优化高
			if (i < size - 5 && '\\' == data.charAt(i)
					&& ('u' == data.charAt(i + 1) || 'U' == data.charAt(i + 1))) {
				tmpData = data.substring(i + 2, i + 6);
				if (tmpData.matches("[0-9A-Fa-f]{4}")) {
					char a = (char) Integer.parseInt(tmpData, 16);
					sb.append(a);
					i += 5;
					continue;
				}
			}
			if (i < size - 7 && '&' == data.charAt(i)
					&& '#' == data.charAt(i + 1) && ';' == data.charAt(i + 7)
					&& ('x' == data.charAt(i + 2) || 'X' == data.charAt(i + 2))) {
				tmpData = data.substring(i + 3, i + 7);
				if (tmpData.matches("[0-9A-Fa-f]{4}")) {
					char a = (char) Integer.parseInt(tmpData, 16);
					sb.append(a);
					i += 7;
					continue;
				}
			}
			sb.append(data.charAt(i));
		}
		return sb.toString();
	}

	public static String parseUnicodeV2(String data) {
		StringBuilder sb = new StringBuilder(data.length());
		StringBuilder tmpData = new StringBuilder(4);
		String obj = "";
		for (int i = 0, size = data.length(); i < size; i++) {
			// tmpData 在长度判断后不可能空指针。优化高
			if (i < size - 5 && '\\' == data.charAt(i)
					&& ('u' == data.charAt(i + 1) || 'U' == data.charAt(i + 1))) {
				tmpData = new StringBuilder(4);
				tmpData.append(data.charAt(i + 2));//V1的替换截取
				tmpData.append(data.charAt(i + 3));
				tmpData.append(data.charAt(i + 4));
				tmpData.append(data.charAt(i + 5));
				obj = tmpData.toString();
				if (obj.matches("[0-9A-Fa-f]{4}")) {
					char a = (char) Integer.parseInt(obj, 16);
					sb.append(a);
					i += 5;
					continue;
				}
			}
			if (i < size - 7 && '&' == data.charAt(i)
					&& '#' == data.charAt(i + 1) && ';' == data.charAt(i + 7)
					&& ('x' == data.charAt(i + 2) || 'X' == data.charAt(i + 2))) {
				tmpData = new StringBuilder(4);
				tmpData.append(data.charAt(i + 3));
				tmpData.append(data.charAt(i + 4));
				tmpData.append(data.charAt(i + 5));
				tmpData.append(data.charAt(i + 6));
				obj = tmpData.toString();
				if (obj.matches("[0-9A-Fa-f]{4}")) {
					char a = (char) Integer.parseInt(obj, 16);
					sb.append(a);
					i += 7;
					continue;
				}
			}
			sb.append(data.charAt(i));
		}
		return sb.toString();
	}

	public static String parseUnicodeV3(String data) {
		StringBuilder sb = new StringBuilder(data.length());
		StringBuilder tmpData = new StringBuilder(4).append("0000");
		String obj = "";
		for (int i = 0, size = data.length(); i < size; i++) {
			if (i < size - 5 && '\\' == data.charAt(i)
					&& ('u' == data.charAt(i + 1) || 'U' == data.charAt(i + 1))) {
				tmpData.setCharAt(0, data.charAt(i + 2));//替换V2的赋值
				tmpData.setCharAt(1, data.charAt(i + 3));
				tmpData.setCharAt(2, data.charAt(i + 4));
				tmpData.setCharAt(3, data.charAt(i + 5));
				obj = tmpData.toString();
				if (obj.matches("[0-9A-Fa-f]{4}")) {
					char a = (char) Integer.parseInt(obj, 16);
					sb.append(a);
					i += 5;
					continue;
				}
			}
			if (i < size - 7 && '&' == data.charAt(i)
					&& '#' == data.charAt(i + 1) && ';' == data.charAt(i + 7)
					&& ('x' == data.charAt(i + 2) || 'X' == data.charAt(i + 2))) {
				tmpData.setCharAt(0, data.charAt(i + 3));
				tmpData.setCharAt(1, data.charAt(i + 4));
				tmpData.setCharAt(2, data.charAt(i + 5));
				tmpData.setCharAt(3, data.charAt(i + 6));
				obj = tmpData.toString();
				if (obj.matches("[0-9A-Fa-f]{4}")) {
					char a = (char) Integer.parseInt(obj, 16);
					sb.append(a);
					i += 7;
					continue;
				}
			}
			sb.append(data.charAt(i));
		}
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		
	    //\u7231\u7231，长度600个。
	    InputStream fis = new FileInputStream(new File("./src/cn/jof/test2020_1_15_testUnicode/1.txt"));
		StringBuilder sb = new StringBuilder();
		byte[] bytes = new byte[1024];
		int len = -1;
		while ((len = fis.read(bytes)) != -1) {
			sb.append(new String(bytes, 0, len));
		}
		System.out.println("文件的内容长度：" + sb.length());
		fis.close();
		final String data = sb.toString();
		new TimeAction() {
			public void action() {
				parseUnicodeV1(data);
			}
		}.say("V1");
		new TimeAction() {
			public void action() {
				parseUnicodeV2(data);
			}
		}.say("V2");
		new TimeAction() {
			public void action() {
				parseUnicodeV3(data);
			}
		}.say("V3");
		new TimeAction() {
			public void action() {
				parseUnicodeV4(data);
			}
		}.say("V4");
	}

	private static boolean check16Number(char... chars) {
		for (char c : chars) {
			if (c < 48 || (c > 57 && c < 65) || (c > 70 && c < 97) || c > 102) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 替换掉数据中的unicode数据如&#x7231;数据或是\u7231(包括大小写)
	 * 
	 * @param data
	 * @return
	 */
	public static String parseUnicodeV4(String data) {
		StringBuilder sb = new StringBuilder(data.length());
		for (int i = 0, size = data.length(); i < size; i++) {
			// tmpData 在长度判断后不可能空指针。优化高
			if (i < size - 5
					&& '\\' == data.charAt(i)
					&& ('u' == data.charAt(i + 1) || 'U' == data.charAt(i + 1))
					&& check16Number(data.charAt(i + 2), data.charAt(i + 3),
							data.charAt(i + 4), data.charAt(i + 5))) {
				char a = (char) Integer.parseInt(//替换掉V3的拼接String
						String.valueOf(new char[] { data.charAt(i + 2),
								data.charAt(i + 3), data.charAt(i + 4),
								data.charAt(i + 5) }), 16);
				sb.append(a);
				i += 5;
				continue;
			}
			if (i < size - 7
					&& '&' == data.charAt(i)
					&& '#' == data.charAt(i + 1)
					&& ';' == data.charAt(i + 7)
					&& ('x' == data.charAt(i + 2) || 'X' == data.charAt(i + 2))
					&& check16Number(data.charAt(i + 3), data.charAt(i + 4),
							data.charAt(i + 5), data.charAt(i + 6))) {//不检测是否16进制数，会被恶意数据玩死比如\\uabcg
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

}

