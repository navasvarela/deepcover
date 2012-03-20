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

	public void addClasses(List<ClassTracker> classesList) {
		for (ClassTracker cls : classesList) {
			addClass(cls);
		}
	}

}
