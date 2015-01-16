/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.himanshu.test.Guavacache;
import com.google.common.cache.*;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author hgusain
 */
public class TestGuavaCache {

  public static int evictCount = 0;

  public static void main(String[] args) throws InterruptedException {

    Cache<Integer, Record> myCache = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.SECONDS)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .concurrencyLevel(4)
            .maximumSize(100)
            .removalListener(new RemovalListener<Object, Object>() {
                @Override
                public void onRemoval(RemovalNotification<Object, Object> notification) {
                    evictCount++;
                    System.out.println(evictCount + "th removed key >> " + notification.getKey()
                            + " with cause " + notification.getCause());
                }
            })
            .recordStats()
            .build();

    int nextKey = 10000;

    for (int i = 0; i < 20; i++) {

        nextKey = nextKey + 1000;

        myCache.put(nextKey, new Record(nextKey, i + " >> " + nextKey));

        Thread.sleep(1000);
    }

    System.out.println("=============================");
    System.out.println("now go to sleep for 20 second");

    Thread.sleep(20000);

    System.out.println("myCache.size() = " + myCache.size());

    for (Integer key : myCache.asMap().keySet()) {
        System.out.println("next exist key in cache is" + key);
    }
    System.out.println("search for key " + 19000 + " : " + myCache.getIfPresent(19000));
    System.out.println("search for key " + 29000 + " : " + myCache.getIfPresent(29000));
}
}

class Record {

  int key;
  String value;

  Record(int key, String value) {
    this.key = key;
    this.value = value;
 }

}