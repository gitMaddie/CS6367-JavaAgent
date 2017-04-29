package org.cs6367.agent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class LogStatementCoverage {
  
  public static HashSet<String> linesCovered = new HashSet<String>();

	public static void LogLinesExecuted(int line, String content) {

    try {
      if(line > 0){
        linesCovered.add(content);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } 
    
	  return;
	}

	
}
