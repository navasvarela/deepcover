package org.deepcover.report;

import static org.deepcover.Checks.NOT_NULL;
import static org.deepcover.Checks.NULL;
import static org.deepcover.Checks.ZERO_OR_EMPTY;
import static org.deepcover.Checks.hasMask;

import java.util.ArrayList;
import java.util.List;

import org.deepcover.ArgTypes;

public class MethodElement extends SourceElement {

	private List<SourceElement> arguments = new ArrayList<SourceElement>();

	public List<SourceElement> getArguments() {
		return arguments;
	}

	public void setArguments(List<SourceElement> theArguments) {
		arguments = theArguments;
	}

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

		arguments.add(arg);
	}

	@Override
	public String getNullChecks() {
		if (totalNull == 0 && totalNotNull == 0)
			processChecks();
		return String.format("%d / %d", actualNull, totalNull);
	}

	protected void processChecks() {
		for (SourceElement arg : arguments) {
			totalNull += arg.totalNotNull;
			totalNotNull += arg.totalNotNull;
			totalEmpty += arg.totalEmpty;
			actualEmpty += arg.actualEmpty;
			actualNotNull += arg.actualNotNull;
			actualNull += arg.actualNull;
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
