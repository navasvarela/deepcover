package org.deepcover.report;

import java.util.ArrayList;
import java.util.List;

public class ReportModel extends SourceElement {

	private List<PackageElement> packages = new ArrayList<PackageElement>();

	public List<PackageElement> getPackages() {
		return packages;
	}

	public void addPackage(PackageElement pkg) {
		packages.add(pkg);
	}

	public PackageElement getPackage(String name) {
		for (SourceElement el : getPackages()) {
			if (name.equals(el.getName()))
				return (PackageElement) el;
		}
		return null;
	}

	public void accummulatePackage(XMLPackage p) {
		PackageElement el = getPackage(p.getName());
		if (el == null) {
			el = new PackageElement();
			el.setName(p.getName());
			packages.add(el);

		}
		for (XMLClass cls : p.getClasses()) {
			el.accumulate(cls);
		}
	}

	public void accummulate(XMLReportDocument doc) {
		for (XMLPackage p : doc.getPackages()) {
			accummulatePackage(p);
		}
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
	public String toString() {
		return "ReportModel [packages=" + packages + "]";
	}

	@Override
	public String getNotNullChecks() {
		if (totalNull == 0 && totalNotNull == 0)
			processChecks();

		return String.format("%d / %d", actualNotNull, totalNotNull);
	}

}
