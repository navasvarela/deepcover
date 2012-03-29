package org.deepcover.report;

import java.util.List;

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

	public void addMethod(XMLMethod method) {
		MethodElement element = new MethodElement();
		element.setName(method.getName());

		for (XMLArgument argument : method.getArguments()) {
			element.addArgument(argument);
		}
		addElement(element);

	}

	public MethodElement getMethodElement(String name) {
		for (SourceElement el : getElements()) {
			if (name.equals(el.getName()))
				return (MethodElement) el;
		}
		return null;
	}

	public void accummulate(XMLMethod method) {
		MethodElement m = getMethodElement(method.getName());
		if (m == null)
			addMethod(method);
		else {
			m.accummulate(method.getArguments());
		}
	}

	public void accummulate(List<XMLMethod> methods) {
		for (XMLMethod m : methods) {
			accummulate(m);
		}
	}

}
