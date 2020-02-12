package cn.jof.utils;

/**
 * 一个简易的时间记录器
 */
public abstract class TimeAction {

	public abstract void action();

	public void say(String msg) {
		long t1 = System.currentTimeMillis();
		action();
		long t2 = System.currentTimeMillis();
		System.out.println(msg + ":" + (t2 - t1));
	}
}