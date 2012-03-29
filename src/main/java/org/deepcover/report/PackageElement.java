package org.deepcover.report;

import java.util.List;

import org.deepcover.ClassTracker;
import org.deepcover.MethodTracker;

public class PackageElement extends SourceElement {

	public void addClass(ClassTracker tracker) {
		ClassElement cls = new ClassElement();
		cls.setName(tracker.getClassName());
		for (MethodTracker method : tracker.getMethodsMap().values()) {
			cls.addMethod(method);
		}
		addElement(cls);
	}

	private void addClass(XMLClass cls) {
		ClassElement element = new ClassElement();
		element.setName(cls.getName());
		for (XMLMethod method : cls.getMethods()) {
			element.addMethod(method);
		}
		addElement(element);
	}

	public void addClasses(List<ClassTracker> classesList) {
		for (ClassTracker cls : classesList) {
			addClass(cls);
		}
	}

	public ClassElement getClassElement(String name) {
		for (SourceElement el : getElements()) {
			if (name.equals(el.getName()))
				return (ClassElement) el;
		}
		return null;
	}

	public void accumulate(XMLClass cls) {
		ClassElement c = getClassElement(cls.getName());
		if (c == null)
			addClass(cls);
		else {
			c.accummulate(cls.getMethods());
		}
	}

}
