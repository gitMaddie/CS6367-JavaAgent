package org.cs6367.agent;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Utilities {
  
  public static Map<String, HashSet<String>> sortByComparator(Map<String, HashSet<String>> unsortMap)
  {

      List<Entry<String, HashSet<String>>> list = new LinkedList<Entry<String, HashSet<String>>>(unsortMap.entrySet());

      // Sorting the list based on values
      Collections.sort(list, new Comparator<Entry<String, HashSet<String>>>()
      {
          public int compare(Entry<String, HashSet<String>> o1,
                  Entry<String, HashSet<String>> o2)
          {
             if(o1.getValue().size() == o2.getValue().size())
               return 0;
             
             if(o1.getValue().size() > o2.getValue().size())
               return -1;
             
             else 
               
               return 1;
             
          }
      });

      // Maintaining insertion order with the help of LinkedList
      Map<String, HashSet<String>> sortedMap = new LinkedHashMap<String, HashSet<String>>();
      for (Entry<String, HashSet<String>> entry : list)
      {
          sortedMap.put(entry.getKey(), entry.getValue());
      }

      return sortedMap;
  }

}
