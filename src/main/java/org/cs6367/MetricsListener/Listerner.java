package org.cs6367.MetricsListener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class Listerner extends RunListener {

  public static long startTime = 0;

  public void testRunStarted(Description description) throws Exception {
    startTime = System.currentTimeMillis();
  }

  public void testFailure(Failure failure) throws java.lang.Exception
  {
    BufferedWriter bw = null;
    FileWriter fw = null;
    StringBuilder writeToFile = new StringBuilder();

    try {
      fw = new FileWriter("failed-tests.txt", true);
      bw = new BufferedWriter(fw);
      bw.write(failure.getDescription().getClassName() + " - " + 
          failure.getDescription().getMethodName() + "-" + 
          (System.currentTimeMillis() - startTime));
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