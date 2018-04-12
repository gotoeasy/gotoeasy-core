package top.gotoeasy.framework.core.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;

public class CasLock {

	private final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

	/**
	 * 锁定
	 * <p/>
	 * 不断争锁直至抢锁成功
	 */
	public void lock() {
		while ( !atomicBoolean.compareAndSet(false, true) ) {
		}
	}

	/**
	 * 尝试锁定
	 * <p/>
	 * 仅争锁一次并返回抢锁结果
	 * @return true:锁定成功/false:锁定失败
	 */
	public boolean tryLock() {
		return atomicBoolean.compareAndSet(false, true);
	}

	/**
	 * 判断是否未锁定
	 * @return true:未锁定/false:已锁定
	 */
	public boolean isNotLock() {
		return !isLock();
	}

	/**
	 * 判断是否已锁定
	 * @return true:已锁定/false:未锁定
	 */
	public boolean isLock() {
		return atomicBoolean.get();
	}

	/**
	 * 解锁
	 */
	public void unLock() {
		atomicBoolean.set(false);
	}

}
