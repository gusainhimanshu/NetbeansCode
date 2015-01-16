/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.himanshu.test.cachebuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hgusain
 */
public class GuavaTester {
   public static void main(String args[]) {
   
      //create a cache for employees based on their employee id
      LoadingCache<String, Employee> employeeCache = 
         CacheBuilder.newBuilder()
            .maximumSize(100) // maximum 100 records can be cached
            .expireAfterWrite(2000, TimeUnit.MILLISECONDS) // cache will expire after 30 minutes of access
            .removalListener(new RemovalListener<String, Employee>() {
                public void onRemoval(RemovalNotification<String, Employee> rem)
                {
                    System.out.println("Removed token as it has met ttl: " + (String)rem.getKey());
                }
                }).build(new CacheLoader<String, Employee>(){ // build the cacheloader
               @Override
               public Employee load(String empId) throws Exception {
                  //make the expensive call
                  return getFromDatabase(empId);
               } 
            });

      try {			
         //on first invocation, cache will be populated with corresponding
         //employee record
         System.out.println("Invocation #1");
         System.out.println(employeeCache.get("100"));
         System.out.println(employeeCache.get("103"));
         System.out.println(employeeCache.get("110"));
          try {
              Thread.sleep(3000);
          } catch (InterruptedException ex) {
              Logger.getLogger(GuavaTester.class.getName()).log(Level.SEVERE, null, ex);
          }
         //second invocation, data will be returned from cache
         System.out.println("Invocation #2");
         System.out.println(employeeCache.get("100"));
         System.out.println(employeeCache.get("103"));
         System.out.println(employeeCache.get("110"));

      }catch (ExecutionException e) {
         e.printStackTrace();
      }
   }

   private static Employee getFromDatabase(String empId) {
   
      Employee e1 = new Employee("Mahesh", "Finance", "100");
      Employee e2 = new Employee("Rohan", "IT", "103");
      Employee e3 = new Employee("Sohan", "Admin", "110");

      Map<String, Employee> database = new HashMap<String, Employee>();
      
      database.put("100", e1);
      database.put("103", e2);
      database.put("110", e3);
      
      System.out.println("Database hit for" + empId);
      
      return database.get(empId);		
   }
}