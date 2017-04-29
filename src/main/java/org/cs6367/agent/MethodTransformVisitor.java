package org.cs6367.agent;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class MethodTransformVisitor extends MethodVisitor implements Opcodes {
  
  String className;
  String methodName;
  int line;

  public MethodTransformVisitor(final MethodVisitor mv, String className, String methodName) {
    super(ASM5, mv);
    this.className = className;
    this.methodName = methodName;
  }

  // method coverage collection
  @Override
  public void visitCode() {
    super.visitCode();
  }


  @Override
  public void visitLineNumber(int line, Label start) {
  this.line=line;
  mv.visitLdcInsn(line);
  mv.visitLdcInsn(methodName+":"+line);
  mv.visitMethodInsn(INVOKESTATIC,
      "org/cs6367/agent/LogStatementCoverage", "LogLinesExecuted",
      "(ILjava/lang/String;)V", false);
    super.visitLineNumber(line, start);
  }

/*  @Override
  public void visitLabel(Label label){
    mv.visitLdcInsn(line);
    mv.visitLdcInsn(className);
    mv.visitLdcInsn(methodName);
    mv.visitMethodInsn(INVOKESTATIC,
        "org/cs6367/agent/LogStatementCoverage", "LogLinesExecuted",
        "(ILjava/lang/String;Ljava/lang/String;)V", false);
  super.visitLabel(label);
  }*/

}