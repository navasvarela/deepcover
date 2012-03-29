package org.deepcover.report;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "package")
public class XMLPackage extends XMLSourceElement {

	@XmlElement(name = "class")
	private List<XMLClass> classes;

	@XmlAttribute
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String theName) {
		name = theName;
	}

	public List<XMLClass> getClasses() {
		return classes;
	}

	public void setClasses(List<XMLClass> theClasses) {
		classes = theClasses;
	}

	@Override
	public String toString() {
		return "XMLPackage [name=" + name + ",classes=" + classes + ", Checks="
				+ super.toString() + "]";
	}
}
