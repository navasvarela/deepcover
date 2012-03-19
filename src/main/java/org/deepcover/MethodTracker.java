package org.deepcover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MethodTracker {
	private final String methodName;

	private final Map<String, Integer> arguments;

	private ArgTypes[] argumentTypes;

	public MethodTracker(String theMethodName, ArgTypes[] theArgumentTypes) {
		methodName = theMethodName;
		arguments = new ConcurrentHashMap<String, Integer>();
		for (int i = 0; i < theArgumentTypes.length; i++) {
			arguments.put("arg" + (i + 1), 0);
		}
		argumentTypes = theArgumentTypes;
	}

	public void addCheck(String argument, Checks check) {
		int currentCheckStatus = arguments.get(argument);
		int mask = check.getMask();
		if (mask != (mask & currentCheckStatus)) {
			arguments.put(argument, currentCheckStatus + mask);
		}

	}

	/**
	 * Assumes checks are given in the same order as the arguments.
	 * 
	 * @param checks
	 */
	public void addChecks(Checks[] checks) {
		List<String> argumentNames = new ArrayList<String>(arguments.keySet());
		for (int i = 0; i < checks.length; i++) {
			addCheck("arg" + (i + 1), checks[i]);
		}
	}

	public Map<String, Integer> getArgumentChecks() {
		return Collections.unmodifiableMap(arguments);
	}

	public int getArgumentCheck(String argumentName) {
		return arguments.get(argumentName);
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof MethodTracker
				&& ((MethodTracker) obj).methodName == methodName;
	}

	public ArgTypes[] getArgumentTypes() {
		return argumentTypes;
	}

	public String getMethodName() {
		return methodName;
	}

	@Override
	public String toString() {
		return "MethodTracker [methodName=" + methodName + ", arguments="
				+ arguments + ", argumentTypes="
				+ Arrays.toString(argumentTypes) + "]";
	}

}
