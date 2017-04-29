package org.cs6367.agent;

import java.lang.instrument.Instrumentation;

public class Agent {
  
  public static String className;

  public static void premain(String agentArgs, Instrumentation inst) {
    className = agentArgs;
    ClassLogger classLogger = new ClassLogger(agentArgs);
    inst.addTransformer(classLogger);
    
  }

}
