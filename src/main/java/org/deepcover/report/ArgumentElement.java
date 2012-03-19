package org.deepcover.report;

import java.util.Locale;

import org.deepcover.ArgTypes;

public class ArgumentElement extends SourceElement {

	private String type;

	public String getType() {
		return type;
	}

	public void setType(ArgTypes theType) {
		type = theType.toString().toLowerCase(Locale.getDefault());
	}
}
