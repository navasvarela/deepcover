package org.deepcover.report;

import java.util.ArrayList;
import java.util.List;

public abstract class SourceElement {
	private static final String D_D = "%d / %d";

	private String name;

	int totalNull = 0;
	int totalNotNull = 0;
	int totalEmpty;
	int actualEmpty;
	int actualNull;
	int actualNotNull;
	private List<SourceElement> elements = new ArrayList<SourceElement>();

	public List<SourceElement> getElements() {
		return elements;
	}

	protected void addElement(SourceElement element) {
		// update totals
		totalNull += element.totalNull;
		totalNotNull += element.totalNotNull;
		totalEmpty += element.totalEmpty;
		actualEmpty += element.actualEmpty;
		actualNotNull += element.actualNotNull;
		actualNull += element.actualNull;
		// add
		elements.add(element);
	}

	public String getName() {
		return name;
	}

	public void setName(String theName) {
		name = theName;
	}

	public String getNullChecks() {
		return String.format(D_D, actualNull, totalNull);
	}

	public String getEmptyChecks() {
		return String.format(D_D, actualEmpty, totalEmpty);
	}

	public String getNotNullChecks() {
		return String.format(D_D, actualNotNull, totalNotNull);
	}

	@Override
	public String toString() {
		return "SourceElement [name=" + name + ", totalNull=" + totalNull
				+ ", totalNotNull=" + totalNotNull + ", totalEmpty="
				+ totalEmpty + ", actualEmpty=" + actualEmpty + ", actualNull="
				+ actualNull + ", actualNotNull=" + actualNotNull + "]";
	}

}
