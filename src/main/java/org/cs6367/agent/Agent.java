package org.cs6367.agent;

import java.lang.instrument.Instrumentation;

public class Agent {

  public static void premain(String agentArgs, Instrumentation inst) {
    
    ClassLogger classLogger = new ClassLogger(agentArgs);
    inst.addTransformer(classLogger);
    
  }

}
