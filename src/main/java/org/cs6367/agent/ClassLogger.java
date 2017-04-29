package org.cs6367.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.cs6367.agent.ClassTransformVisitor;

public class ClassLogger implements ClassFileTransformer {
  
  String args;
  
  ClassLogger(String args){
    this.args = args;
  }

  public byte[] transform(ClassLoader classLoader, 
                          String className,
                          Class<?> classBeingRedefined, 
                          ProtectionDomain protectionDomain,
                          byte[] bytes) throws IllegalClassFormatException {

   // ASM Code
    if (className.contains(args)) {
      ClassReader reader = new ClassReader(bytes);
      ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
      ClassTransformVisitor visitor = new ClassTransformVisitor(writer);
      reader.accept(visitor, ClassReader.EXPAND_FRAMES);
      return writer.toByteArray();
    } else {
      return bytes;
    }

  }

}
