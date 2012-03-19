package org.deepcover.report;

public abstract class SourceElement {
	private String name;

	int totalNull = 0;
	int totalNotNull = 0;
	int totalEmpty;
	int actualEmpty;
	int actualNull;
	int actualNotNull;

	public String getName() {
		return name;
	}

	public void setName(String theName) {
		name = theName;
	}

	public String getNullChecks() {
		return String.format("%d / %d", actualNull, totalNull);
	}

	public String getEmptyChecks() {
		return String.format("%d / %d", actualEmpty, totalEmpty);
	}

	public String getNotNullChecks() {
		return String.format("%d / %d", actualNotNull, totalNotNull);
	}

}
