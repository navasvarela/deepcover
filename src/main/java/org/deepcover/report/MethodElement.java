package org.deepcover.report;

import static org.deepcover.Checks.NOT_NULL;
import static org.deepcover.Checks.NULL;
import static org.deepcover.Checks.ZERO_OR_EMPTY;
import static org.deepcover.Checks.hasMask;

import java.util.List;

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

	public void addArgument(XMLArgument argument) {
		ArgumentElement arg = new ArgumentElement();
		arg.setName(argument.getName());
		String[] notNullChecks = argument.getNotNullChecks().split("/");
		String[] nullChecks = argument.getNullChecks().split("/");
		arg.totalNotNull = 1;
		arg.totalNull = 1;
		arg.actualNull = Integer.parseInt(nullChecks[0].trim());
		arg.actualNotNull = Integer.parseInt(notNullChecks[0].trim());

		if (argument.getEmptyChecks() != null) {
			String[] emptyChecks = argument.getEmptyChecks().split("/");
			arg.totalEmpty = Integer.parseInt(emptyChecks[1].trim());
			arg.actualEmpty = Integer.parseInt(emptyChecks[0].trim());
		}

		addElement(arg);
	}

	public void accummulate(List<XMLArgument> arguments) {
		for (SourceElement arg : getElements()) {
			for (XMLArgument xmlArg : arguments) {
				if (xmlArg.getName().equals(arg.getName())) {
					arg.actualEmpty = arg.actualEmpty
							| Integer.parseInt(xmlArg.getEmptyChecks().split(
									"/")[0].trim());
					arg.actualNull = arg.actualNull
							| Integer.parseInt(xmlArg.getNullChecks()
									.split("/")[0].trim());
					arg.actualNotNull = arg.actualNotNull
							| Integer.parseInt(xmlArg.getNotNullChecks().split(
									"/")[0].trim());
				}
			}
		}
	}

}
