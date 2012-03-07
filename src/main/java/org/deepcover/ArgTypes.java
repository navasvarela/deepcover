package org.deepcover;

import static org.deepcover.Checks.*;
public enum ArgTypes {
	
	NUMBER(new Checks[]{NULL, ZERO_OR_EMPTY, NOT_NULL}), STRING(new Checks[]{NULL, ZERO_OR_EMPTY, NOT_NULL}), OBJECT(new Checks[]{NULL, NOT_NULL});

	private final Checks[] checks;
	

	private ArgTypes(Checks[] theChecks) {
		checks = theChecks;
	}
	public Checks[] getChecks() {
		return checks;
	}	
	
}
