package org.deepcover.report;

import org.deepcover.ArgTypes;
import org.deepcover.MethodTracker;

public class ClassElement extends SourceElement {

	public void addMethod(MethodTracker tracker) {
		MethodElement element = new MethodElement();
		element.setName(tracker.getMethodName());

		ArgTypes[] argTypes = tracker.getArgumentTypes();
		for (String argument : tracker.getArgumentChecks().keySet()) {
			int argumentOrder = Integer.parseInt(argument.substring(3));
			element.addArgument(argTypes[argumentOrder - 1], argument,
					tracker.getArgumentCheck(argument));

		}
		addElement(element);

	}

}
