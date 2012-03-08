package org.deepcover;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MethodTracker {
	private final String methodName;
	
	private final Map<String, Integer> arguments;

	public MethodTracker(String theMethodName, String[] theArguments) {
		methodName = theMethodName;
		arguments = new ConcurrentHashMap<String, Integer>();
		for (String argument: theArguments) {
			arguments.put(argument, 0);
		}
	}
	
	public void addCheck(String argument, Checks check) {
		int currentCheckStatus = arguments.get(argument);
		int mask = check.getMask();
		if (mask != (mask & currentCheckStatus)) {
			arguments.put(argument, currentCheckStatus + mask);
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
		return obj != null && obj instanceof MethodTracker && ((MethodTracker) obj).methodName == methodName;
	}

	@Override
	public String toString() {
		return "MethodTracker [methodName=" + methodName + ", arguments="
				+ arguments + "]";
	}
	
	
}
