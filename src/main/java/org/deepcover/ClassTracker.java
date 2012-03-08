package org.deepcover;

import java.util.Map;

public class ClassTracker {
	
	private final String className;
	
	private Map<String, MethodTracker>  methods;

	public ClassTracker(String theClassName, Map<String, MethodTracker> theMethods) {
		super();
		this.className = theClassName;
		this.methods = theMethods;
	}
	
	public void addCheckForMethod(String methodName, String argumentName, Checks check) {
		MethodTracker tracker = methods.get(methodName);
		tracker.addCheck(argumentName, check);
	}

	@Override
	public String toString() {
		return "ClassTracker [className=" + className + ", methods=" + methods
				+ "]";
	}
	
	
	
	

}
