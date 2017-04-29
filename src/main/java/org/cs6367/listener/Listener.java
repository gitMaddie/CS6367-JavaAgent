package org.cs6367.listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.cs6367.agent.Agent;
import org.cs6367.agent.LogStatementCoverage;
import org.cs6367.agent.Utilities;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class Listener extends RunListener {

  public static Map<String, HashSet<String>> coverageStateMap = new HashMap<String, HashSet<String>>();

  /*
   * public void testRunStarted(Description description) throws Exception {
   * System.out.println("Number of tests to execute: " +
   * description.testCount()); }
   * 
   * public void testRunFinished(Result result) throws Exception {
   * System.out.println("Number of tests executed: " + result.getRunCount()); }
   */

  public void testStarted(Description description) throws Exception {
    LogStatementCoverage.linesCovered = new HashSet<String>();
    return;
  }

  public void testFinished(Description description) throws Exception {
    // add all lines covered by this test
    String key = description.getClassName();// + ":" + description.getMethodName();

    if (!coverageStateMap.containsKey(key))
      coverageStateMap.put(key, LogStatementCoverage.linesCovered);

    return;
  }

  public void testRunFinished(Result result) throws Exception {

    // sort the hashmap by value
    Map<String, HashSet<String>> sortedMap = Utilities
        .sortByComparator(coverageStateMap);

    // write the sorted map in the file
    BufferedWriter bw = null;
    FileWriter fw = null;
    StringBuffer writeToFile = new StringBuffer();
    
    writeToFile.append("package " + Agent.className.replace("/", ".") + ";\n\n");
    writeToFile.append("import org.junit.runner.RunWith;");
    writeToFile.append("\n");
    writeToFile.append("import org.junit.runners.Suite;");
    writeToFile.append("\n");
    writeToFile.append("@RunWith(Suite.class)\n\n");
    writeToFile.append("@Suite.SuiteClasses({");
    writeToFile.append("\n");
    
    try {
      String content = new String();

      for (Map.Entry<String, HashSet<String>> entry : sortedMap.entrySet()) {
        content = "\t" + entry.getKey()+ ".class,\n"; // entry.getValue().size() + "\n";
        writeToFile.append(content);
      }
      

      writeToFile.append("})\n");
      writeToFile.append("public class TotalStrategyTestSuite {\n}");
      fw = new FileWriter("src/test/java/"+Agent.className+"/TotalStrategyTestSuite.java");
      bw = new BufferedWriter(fw);
      bw.write(writeToFile.toString());
      bw.newLine();

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bw != null)
          bw.close();
        if (fw != null)
          fw.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    /***** Total Stratgey ends *********/
    writeToFile = new StringBuffer();
    writeToFile.append("package " + Agent.className.replace("/", ".") + ";\n\n");
    writeToFile.append("import org.junit.runner.RunWith;");
    writeToFile.append("\n");
    writeToFile.append("import org.junit.runners.Suite;");
    writeToFile.append("\n");
    writeToFile.append("@RunWith(Suite.class)\n\n");
    writeToFile.append("@Suite.SuiteClasses({");
    writeToFile.append("\n");
    
    while(sortedMap.size()>0){

    String testName = new String();
    HashSet<String> testLines = new HashSet<String>();

    for (Entry<String, HashSet<String>> entry : sortedMap.entrySet()) {
      testName = entry.getKey();
      writeToFile.append("\t" + testName+ ".class,\n" );
      break;
    }
    
    // remove this test from the hashmap - add it to a file
    sortedMap.remove(testName);

    // all the lines covered by the test

    Iterator<String> it = testLines.iterator();

    while (it.hasNext()) {
      // pick the current line and remove it from all the tests
      String line = it.next();
      
      // iterate through the hashmap and remove this line from other tests
      for (Entry<String, HashSet<String>> entry : sortedMap.entrySet()) {
        HashSet<String> test = entry.getValue();
        if (test.contains(line)) {
          test.remove(line);
        }
        entry.setValue(test);
      }
      
    }
    // all entries of the sortedMap have the new set of lines now
    
    // sort this new map again
    sortedMap = Utilities
        .sortByComparator(sortedMap);

    }
    
    writeToFile.append("})\n");
    writeToFile.append("public class AdditionalStrategyTestSuite {\n}");
    
    try {
      fw = new FileWriter("src/test/java/"+Agent.className+"/AdditionalStrategyTestSuite.java");
      bw = new BufferedWriter(fw);
      bw.write(writeToFile.toString());
      bw.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bw != null)
          bw.close();
        if (fw != null)
          fw.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }
}