package base.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class MyPriorityTransferQueue<E> extends PriorityBlockingQueue<E> implements TransferQueue<E> {

    // the count of waiting consumer
    private AtomicInteger counter;

    private LinkedBlockingQueue<E> transfered;

    private ReentrantLock lock;

    public MyPriorityTransferQueue() {
        counter = new AtomicInteger(0);
        lock = new ReentrantLock();
        transfered = new LinkedBlockingQueue<E>();
    }

    @Override
    public boolean tryTransfer(E e) {
        lock.lock();
        boolean value;
        if (counter.get() == 0) {
            value = false;
        } else {
            put(e);
            value = true;
        }
        lock.unlock();
        return value;
    }

    @Override
    public void transfer(E e) throws InterruptedException {
        lock.lock();
        if (counter.get() != 0) {
            put(e);
            lock.unlock();
        } else {
            transfered.add(e);
            lock.unlock();
            synchronized (e) {
                e.wait();
            }
        }
    }

    @Override
    public boolean tryTransfer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        lock.lock();
        if (counter.get() != 0) {
            put(e);
            lock.unlock();
            return true;
        } else {
            transfered.add(e);
            long newTimeout = TimeUnit.MILLISECONDS.convert(timeout, unit);
            lock.unlock();
            e.wait(newTimeout);
            lock.lock();

            if (transfered.contains(e)) {
                transfered.remove(e);
                lock.unlock();
                return false;
            } else {
                lock.unlock();
                return true;
            }
        }
    }

    @Override
    public boolean hasWaitingConsumer() {
        return (counter.get() != 0);
    }

    @Override
    public int getWaitingConsumerCount() {
        return counter.get();
    }

    @Override
    public E take() throws InterruptedException {
        lock.lock();
        counter.incrementAndGet();

        // 12.如果在 transferred queue 中无任何元素。释放锁并使用 take() 方法尝试从queue中获取元素，此方法将让线程进入睡眠直到有元素可以消耗。
        E value = transfered.poll();
        if (value == null) {
            lock.unlock();
            value = super.take();
            lock.lock();

            // 13. 否则，从transferred queue 中取走元素并唤醒正在等待要消耗元素的线程（如果有的话）。
        } else {
            synchronized (value) {
                value.notify();
            }
        }

        // 14. 最后，增加正在等待的消费者的数量并释放锁。
        counter.decrementAndGet();
        lock.unlock();
        return value;
    }

}