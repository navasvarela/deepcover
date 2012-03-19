package org.deepcover.report;

import java.util.ArrayList;
import java.util.List;

import org.deepcover.ArgTypes;
import org.deepcover.MethodTracker;

public class ClassElement extends SourceElement {

	private List<MethodElement> methods = new ArrayList<MethodElement>();

	public List<MethodElement> getMethods() {
		return methods;
	}

	public void addMethod(MethodTracker tracker) {
		MethodElement element = new MethodElement();
		element.setName(tracker.getMethodName());

		ArgTypes[] argTypes = tracker.getArgumentTypes();
		for (String argument : tracker.getArgumentChecks().keySet()) {
			int argumentOrder = Integer.parseInt(argument.substring(3));
			element.addArgument(argTypes[argumentOrder - 1], argument,
					tracker.getArgumentCheck(argument));

		}
		methods.add(element);

	}

	@Override
	public String getNullChecks() {
		if (totalNull == 0 && totalNotNull == 0)
			processChecks();
		return String.format("%d / %d", actualNull, totalNull);
	}

	protected void processChecks() {

		for (MethodElement method : methods) {
			totalNull += method.totalNotNull;
			totalNotNull += method.totalNotNull;
			totalEmpty += method.totalEmpty;
			actualEmpty += method.actualEmpty;
			actualNotNull += method.actualNotNull;
			actualNull += method.actualNull;
		}
	}

	@Override
	public String getEmptyChecks() {
		if (totalNull == 0 && totalNotNull == 0)
			processChecks();

		return String.format("%d / %d", actualEmpty, totalEmpty);
	}

	@Override
	public String getNotNullChecks() {
		if (totalNull == 0 && totalNotNull == 0)
			processChecks();

		return String.format("%d / %d", actualNotNull, totalNotNull);
	}
}
