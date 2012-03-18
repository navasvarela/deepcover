package org.deepcover;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ClassTracker {

	private final String className;

	private Map<String, MethodTracker> methods;

	public ClassTracker(String theClassName,
			Map<String, MethodTracker> theMethods) {
		super();
		this.className = theClassName;
		this.methods = theMethods;
	}

	public ClassTracker(String theClassName) {
		this.className = theClassName;
		methods = new HashMap<String, MethodTracker>();
	}

	public void addCheckForMethod(String methodName, String argumentName,
			Checks check) {

		MethodTracker tracker = methods.get(methodName);
		tracker.addCheck(argumentName, check);
	}

	public void calculateChecksForMethod(String methodName,
			Object[] argumentValues) {
		MethodTracker tracker = methods.get(methodName);
		Checks[] checks = new Checks[argumentValues.length];
		ArgTypes[] argumentTypes = tracker.getArgumentTypes();
		for (int i = 0; i < checks.length; i++) {
			if (argumentValues[i] == null) {
				checks[i] = Checks.NULL;
			} else {
				if (argumentTypes[i] == ArgTypes.NUMBER
						&& ((Number) argumentValues[i]).intValue() == 0) {
					checks[i] = Checks.ZERO_OR_EMPTY;

				} else if (argumentTypes[i] == ArgTypes.STRING
						&& ((String) argumentValues[i]).length() == 0) {
					checks[i] = Checks.ZERO_OR_EMPTY;

				} else {
					checks[i] = Checks.NOT_NULL;
				}
			}
		}
		tracker.addChecks(checks);

	}

	public void addMethod(String methodName, ArgTypes[] argumentTypes) {
		MethodTracker tracker = new MethodTracker(methodName, argumentTypes);
		methods.put(methodName, tracker);
	}

	public Map<String, MethodTracker> getMethodsMap() {
		return Collections.unmodifiableMap(methods);
	}

	@Override
	public String toString() {
		return "ClassTracker [className=" + className + ", methods=" + methods
				+ "]";
	}

}
