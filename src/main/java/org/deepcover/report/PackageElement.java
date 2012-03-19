package org.deepcover.report;

import java.util.ArrayList;
import java.util.List;

import org.deepcover.ClassTracker;
import org.deepcover.MethodTracker;

public class PackageElement extends SourceElement {

	private List<ClassElement> classes = new ArrayList<ClassElement>();

	public List<ClassElement> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassElement> theClasses) {
		classes = theClasses;
	}

	public void addClass(ClassTracker tracker) {
		ClassElement cls = new ClassElement();
		cls.setName(tracker.getClassName());
		for (MethodTracker method : tracker.getMethodsMap().values()) {
			cls.addMethod(method);
		}
		classes.add(cls);
	}

	public void addClasses(List<ClassTracker> classesList) {
		for (ClassTracker cls : classesList) {
			addClass(cls);
		}
	}

	@Override
	public String getNullChecks() {
		if (totalNull == 0 && totalNotNull == 0)
			processChecks();
		return String.format("%d / %d", actualNull, totalNull);
	}

	protected void processChecks() {
		for (ClassElement cls : classes) {
			totalNull += cls.totalNotNull;
			totalNotNull += cls.totalNotNull;
			totalEmpty += cls.totalEmpty;
			actualEmpty += cls.actualEmpty;
			actualNotNull += cls.actualNotNull;
			actualNull += cls.actualNull;
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
