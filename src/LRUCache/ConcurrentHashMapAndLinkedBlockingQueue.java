package LRUCache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Jacob
 * @Description: 使用 ConcurrentHashMap 和 LinkedBlockingQueue 实现的线程安全 LRU 缓存
 * @Date: 2024-07-25 21:30
 * @Version: 1.0
 */
public class ConcurrentHashMapAndLinkedBlockingQueue {
    private final int capacity;
    private final ConcurrentHashMap<Integer, Integer> cache;
    private final LinkedBlockingQueue<Integer> accessOrder;
    private final ReentrantLock lock;

    public ConcurrentHashMapAndLinkedBlockingQueue(int capacity) {
        this.capacity = capacity;
        this.cache = new ConcurrentHashMap<>(capacity);
        this.accessOrder = new LinkedBlockingQueue<>(capacity);
        this.lock = new ReentrantLock();
    }

    public int get(int key) {
        lock.lock();
        try {
            if (!cache.containsKey(key)) {
                return -1;
            }
            // 更新访问顺序
            accessOrder.remove(key);
            accessOrder.offer(key);
            return cache.get(key);
        } finally {
            lock.unlock();
        }
    }

    public void put(int key, int value) {
        lock.lock();
        try {
            if (cache.containsKey(key)) {
                // 更新已有的值并更新访问顺序
                cache.put(key, value);
                accessOrder.remove(key);
                accessOrder.offer(key);
            } else {
                if (cache.size() == capacity) {
                    // 移除最久未使用的条目
                    Integer leastUsedKey = accessOrder.poll();
                    if (leastUsedKey != null) {
                        cache.remove(leastUsedKey);
                    }
                }
                // 插入新值
                cache.put(key, value);
                accessOrder.offer(key);
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ConcurrentHashMapAndLinkedBlockingQueue lruCache = new ConcurrentHashMapAndLinkedBlockingQueue(3);

        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.put(3, 3);

        System.out.println(lruCache.get(1)); // 输出1
        lruCache.put(4, 4);
        System.out.println(lruCache.get(2)); // 输出-1，因为key为2的条目已被移除
        lruCache.put(5, 5);
        System.out.println(lruCache.get(3)); // 输出-1，因为key为3的条目已被移除
        System.out.println(lruCache.get(4)); // 输出4
        System.out.println(lruCache.get(5)); // 输出5
    }
}
