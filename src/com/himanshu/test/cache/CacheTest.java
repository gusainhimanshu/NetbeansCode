/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.himanshu.test.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author hgusain
 */
public class CacheTest {
    private Map<String, String> memoryMap = null;

    public CacheTest() {
        init(715,3);
    }
    private void init(int ttl, int maxEntries)
  {
      
    System.out.println("Initializing InMem Cache with : ttl=" + ttl + " Minutes" + " MaxEntries=" + maxEntries);
  /*Using the from spec for using the command from a commandline or from a config spec*/  
    /*String spec = "maximumSize=10000,expireAfterWrite=10m";
    
    Cache myCache = CacheBuilder.newBuilder().from(spec).removalListener(new RemovalListener<String, String>(){
        public void onRemoval(RemovalNotification<String, String> rem)
        {
        System.out.println("Removed token as it has met ttl: " + (String)rem.getKey());
      }
    }).build();
    */
   /*Using the expireAfterWrite and maximumSize method*/ 
    Cache myCache = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).maximumSize(maxEntries).removalListener(new RemovalListener<String, String>()
    {
      public void onRemoval(RemovalNotification<String, String> rem)
      {
        System.out.println("Removed token as it has met ttl: " + (String)rem.getKey());
      }
    }).build();

    this.memoryMap = myCache.asMap();
  }
    public static void main(String args[]){
        CacheTest cache = new CacheTest();
        cache.memoryMap.put("hello", "world");
        cache.memoryMap.put("test1", "old");
        cache.memoryMap.put("test2", "get");
        System.out.println(cache.memoryMap.get("hello"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CacheTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(cache.memoryMap.get("hello"));
        System.out.println(cache.memoryMap.get("test1"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CacheTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(cache.memoryMap.get("test1"));
        cache.memoryMap.put("test", "new");
        System.out.println(cache.memoryMap.get("test"));
        System.out.println(cache.memoryMap.get("test1"));
        System.out.println(cache.memoryMap.get("test2"));// since the entry got deleted it returns null
    }
}
