package org.deepcover.report;

import static org.deepcover.Checks.NOT_NULL;
import static org.deepcover.Checks.NULL;
import static org.deepcover.Checks.ZERO_OR_EMPTY;
import static org.deepcover.Checks.hasMask;

import org.deepcover.ArgTypes;

public class MethodElement extends SourceElement {

	public void addArgument(ArgTypes type, String name, int check) {
		ArgumentElement arg = new ArgumentElement();
		arg.setName(name);
		arg.setType(type);
		arg.totalNotNull = 1;
		arg.totalNull = 1;
		arg.actualNull = hasMask(check, NULL) ? 1 : 0;
		arg.actualNotNull = hasMask(check, NOT_NULL) ? 1 : 0;

		if (type != ArgTypes.OBJECT) {
			arg.totalEmpty = 1;
			arg.actualEmpty = hasMask(check, ZERO_OR_EMPTY) ? 1 : 0;
		}

		addElement(arg);
	}

}
