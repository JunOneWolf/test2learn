package cn.jof.test2020_1_16_testStringConcat;


/**
 * <pre>
 * 测试不同情况下的字符串拼接
 * </pre>
 * 
 * @author JunOneWolf
 * @time 2020-1-16
 */
public class TestStringConcat {

	/**
	 * +号拼接
	 */
	private static String addV1(int s) {
		String sql = "";
		for (int i = 0; i < s; i++) {
			sql += i;
		}
		return sql;
	}

	/**
	 * +号拼接
	 */
	private static String addV1_jdk8(int s) {
		String sql = "";
		for (int i = 0; i < s; i++) {
			sql = new StringBuffer(sql).append(i).toString();// sql += i;jdk1.8版本在编译后的优化结果
		}
		return sql;
	}

	private static String addV2(int s) {
		String sql = "";
		for (int i = 0; i < s; i++) {
			sql = sql.concat(String.valueOf(i));
		}
		return sql.toString();
	}

	/**
	 * 线程安全拼接
	 */
	private static String addV3(int s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s; i++) {
			sb.append(i);
		}
		return sb.toString();
	}

	/**
	 * 线程不安全的效率最高的拼接。
	 */
	private static String addV4(int s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s; i++) {
			sb.append(i);
		}
		return sb.toString();
	}

	private static String addV5(int s, int baseSize) {
		StringBuilder sb = new StringBuilder(baseSize);//10W快一点点，100W又会慢很多。还是忘记这个吧。
		for (int i = 0; i < s; i++) {
			sb.append(i);
		}
		return sb.toString();
	}

	/**
	 * org.apache.commons.lang3.StringUtils的方法
	 */
	public static String join(Object array[], String separator, int startIndex,
			int endIndex) {
		if (array == null)
			return null;
		if (separator == null)
			separator = "";
		int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0)
			return "";
		StringBuilder buf = new StringBuilder(noOfItems * 16);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex)
				buf.append(separator);
			if (array[i] != null)
				buf.append(array[i]);
		}

		return buf.toString();
	}

	public static void main(String[] args) {
		final int c1 = 20000;
		new TimeAction() {
			void action() {
				addV1(c1);// 评价：不堪大用
			}
		}.start("V1 " + c1 + "次");
		new TimeAction() {
			void action() {
				addV1_jdk8(c1);// 单次就很快，循环中就很差。
			}
		}.start("V1_增强 " + c1 + "次");
		new TimeAction() {
			void action() {
				addV2(c1);// 评价：不堪大用
			}
		}.start("V2 " + c1 + "次");
		if (true) {
			final int c2 = c1*50 ;
			new TimeAction() {
				void action() {
					addV3(c2);
				}
			}.start("V3 " + c2 + "次");
			new TimeAction() {
				void action() {
					addV4(c2);
				}
			}.start("V4 " + c2 + "次");
			int c = 1024;
			for (int i = 0; i < 12; i++) {
				c *= 2;
				final int tc = c;
				new TimeAction() {
					void action() {
						addV5(c2, tc);
					}
				}.start("V5 " + c2 + " 次，初始化大小" + tc);
			}
		}
		if (true) {
			final int c2 = c1 * 500;
			new TimeAction() {
				void action() {
					addV3(c2);
				}
			}.start("V3 " + c2 + "次");
			new TimeAction() {
				void action() {
					addV4(c2);
				}
			}.start("V4 " + c2 + "次");
			int c = 1024;
			for (int i = 0; i < 12; i++) {
				c *= 2;
				final int tc = c;
				new TimeAction() {
					void action() {
						addV5(c2, tc);
					}
				}.start("V5 " + c2 + " 次，初始化大小" + tc);
			}
		}
	}
}

abstract class TimeAction {
	abstract void action();

	void start(String msg) {
		long t1 = System.currentTimeMillis();
		action();
		long t2 = System.currentTimeMillis();
		System.out.println(msg + "花费时间(毫秒)：" + (t2 - t1));
	};
}
