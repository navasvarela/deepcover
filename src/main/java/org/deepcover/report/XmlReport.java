package org.deepcover.report;

import java.util.ArrayList;
import java.util.List;

public class XmlReport extends SourceElement {

	private List<PackageElement> packages = new ArrayList<PackageElement>();

	public List<PackageElement> getPackages() {
		return packages;
	}

	public void addPackage(PackageElement pkg) {
		packages.add(pkg);
	}

	@Override
	public String getNullChecks() {
		if (totalNull == 0 && totalNotNull == 0)
			processChecks();
		return String.format("%d / %d", actualNull, totalNull);
	}

	protected void processChecks() {
		for (PackageElement pkg : packages) {
			totalNull += pkg.totalNotNull;
			totalNotNull += pkg.totalNotNull;
			totalEmpty += pkg.totalEmpty;
			actualEmpty += pkg.actualEmpty;
			actualNotNull += pkg.actualNotNull;
			actualNull += pkg.actualNull;
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
