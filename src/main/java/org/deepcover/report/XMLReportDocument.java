package org.deepcover.report;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "deepcover")
public class XMLReportDocument extends XMLSourceElement {

	@XmlElement(name = "package")
	private List<XMLPackage> packages;

	public List<XMLPackage> getPackages() {
		return packages;
	}

	public void setPackages(List<XMLPackage> thePackages) {
		packages = thePackages;
	}

	@Override
	public String toString() {
		return "XMLReportDocument [packages=" + packages + ", Checks="
				+ super.toString() + "]";
	}

}
