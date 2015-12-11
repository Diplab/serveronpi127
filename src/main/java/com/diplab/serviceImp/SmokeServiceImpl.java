package com.diplab.serviceImp;

import java.util.Collections;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import com.diplab.device.RpiSmoke;
import com.diplab.service.SmokeService;

public class SmokeServiceImpl extends Thread implements SmokeService {

	LinkedBlockingDeque<Double> queue = new AutoDiscardingDeque<>(30);
	// 0>stop, 1>Start
	AtomicInteger state = new AtomicInteger(1);

	@Override
	public void run() {
		while (true) {
			if (state.get() == 1) {
				double smoke = RpiSmoke.get();
				queue.offerFirst(smoke);
				System.out.format("offerFirst[%f]\n", smoke);
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public double getSmokePpm() throws InterruptedException {
		if (queue.size() == 0) {
			System.out.format("In get, size = 0, block\n");
			return queue.take();
		} else {
			Double max = Collections.max(queue);
			System.out.format("queue have [%d]\n", queue.size());
			queue.clear();
			return max;
		}
	}

}

class AutoDiscardingDeque<E> extends LinkedBlockingDeque<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AutoDiscardingDeque() {
		super();
	}

	public AutoDiscardingDeque(int capacity) {
		super(capacity);
	}

	@Override
	public synchronized boolean offerFirst(E e) {
		if (remainingCapacity() == 0) {
			E removeLast = removeLast();
			System.out.format("Remove last element [%s]\n", removeLast);
		}
		super.offerFirst(e);
		return true;
	}
}