package edu.nju.cs.inform.core.callGraph;

/*
 * Copyright (c) 2011 - Georgios Gousios <gousiosg@gmail.com>
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.*;
/**
 * The simplest of method visitors, prints any invoked method signature for all
 * method invocations.
 * 
 * Class copied with modifications from CJKM: http://www.spinellis.gr/sw/ckjm/
 */
public class MethodVisitor extends EmptyVisitor {

	JavaClass visitedClass;
	private MethodGen mg;
	private ConstantPoolGen cp;
	private String format;

	public MethodVisitor(MethodGen m, JavaClass jc) {
		visitedClass = jc;
		mg = m;
		cp = mg.getConstantPool();
		format = "M:" + visitedClass.getClassName() + ":" + mg.getName() + " "
				+ "(%s)%s:%s";
	}

	public void start() {
		if (mg.isAbstract() || mg.isNative())
			return;
		for (InstructionHandle ih = mg.getInstructionList().getStart(); ih != null; ih = ih
				.getNext()) {
			Instruction i = ih.getInstruction();

			if (!visitInstruction(i))
				i.accept(this);
		}
	}

	private boolean visitInstruction(Instruction i) {
		short opcode = i.getOpcode();

		return ((InstructionConstants.INSTRUCTIONS[opcode] != null)
				&& !(i instanceof ConstantPushInstruction) && !(i instanceof ReturnInstruction));
	}

	@Override
	public void visitINVOKEVIRTUAL(INVOKEVIRTUAL i) {
		if (JCallGraph.isOwnMethod(format) && JCallGraph.isOwnMethod(i.getMethodName(cp).toString())
				&& JCallGraph.isOwnMethod(i.getReferenceType(cp).toString())) {
			// System.out.println(String.format(format,"M",i.getReferenceType(cp),i.getMethodName(cp)));
			// SK - add methodnames to callgraph relations
			JCallGraph.addToCallGraph(mg.getClassName() + "."
					+ mg.getMethod().getName(), i.getReferenceType(cp) + "."
					+ i.getMethodName(cp));
			JCallGraph.addToIsCalledByGraph(mg.getClassName() + "."
					+ mg.getMethod().getName(), i.getReferenceType(cp) + "."
					+ i.getMethodName(cp));
			// System.out.println("Callgraph Method Visit "
			// +mg.getClassName()+"."+mg.getMethod().getName()+" "+
			// i.getReferenceType(cp)+"."+i.getMethodName(cp));
		}
	}

	@Override
	public void visitINVOKEINTERFACE(INVOKEINTERFACE i) {
		if (JCallGraph.isOwnMethod(format) && JCallGraph.isOwnMethod(i.getMethodName(cp).toString())
				&& JCallGraph.isOwnMethod(i.getReferenceType(cp).toString())) {
			// System.out.println(String.format(format,"I",i.getReferenceType(cp),i.getMethodName(cp)));
			/** SK - add methodnames to callgraph relations */
			JCallGraph.addToCallGraph(mg.getClassName() + "."
					+ mg.getMethod().getName(), i.getReferenceType(cp) + "."
					+ i.getMethodName(cp));
			JCallGraph.addToIsCalledByGraph(mg.getClassName() + "."
					+ mg.getMethod().getName(), i.getReferenceType(cp) + "."
					+ i.getMethodName(cp));
			// System.out.println("Callgraph Method Visit "
			// +mg.getClassName()+"."+mg.getMethod().getName()+" " +
			// i.getReferenceType(cp)+"."+i.getMethodName(cp));
		}
	}

	@Override
	public void visitINVOKESPECIAL(INVOKESPECIAL i) {
		if (JCallGraph.isOwnMethod(format) && JCallGraph.isOwnMethod(i.getMethodName(cp).toString())
				&& JCallGraph.isOwnMethod(i.getReferenceType(cp).toString())) {
			// System.out.println(String.format(format,"O",i.getReferenceType(cp),i.getMethodName(cp)));
			/** SK - add methodnames to callgraph relations */
			JCallGraph.addToCallGraph(mg.getClassName() + "."
					+ mg.getMethod().getName(), i.getReferenceType(cp) + "."
					+ i.getMethodName(cp));
			JCallGraph.addToIsCalledByGraph(mg.getClassName() + "."
					+ mg.getMethod().getName(), i.getReferenceType(cp) + "."
					+ i.getMethodName(cp));
			// System.out.println("Callgraph Method Visit "
			// +mg.getClassName()+"."+mg.getMethod().getName()+" " +
			// i.getReferenceType(cp)+"."+i.getMethodName(cp));
		}
	}

	@Override
	public void visitINVOKESTATIC(INVOKESTATIC i) {
		if (JCallGraph.isOwnMethod(format) && JCallGraph.isOwnMethod(i.getMethodName(cp).toString())
				&& JCallGraph.isOwnMethod(i.getReferenceType(cp).toString())) {
			// System.out.println(String.format(format,"S",i.getReferenceType(cp),i.getMethodName(cp)));
			/** SK - add methodnames to callgraph relations */
			JCallGraph.addToCallGraph(mg.getClassName() + "."
					+ mg.getMethod().getName(), i.getReferenceType(cp) + "."
					+ i.getMethodName(cp));
			JCallGraph.addToIsCalledByGraph(mg.getClassName() + "."
					+ mg.getMethod().getName(), i.getReferenceType(cp) + "."
					+ i.getMethodName(cp));
			// System.out.println("Callgraph Method Visit "
			// +mg.getClassName()+"."+mg.getMethod().getName()+" "+
			// i.getReferenceType(cp)+"."+i.getMethodName(cp));
		}
	}
}
