package org.deepcover.report;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "class")
public class XMLClass extends XMLSourceElement {

	@XmlElement(name = "method")
	private List<XMLMethod> methods;

	@XmlAttribute
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String theName) {
		name = theName;
	}

	public List<XMLMethod> getMethods() {
		return methods;
	}

	public void setMethods(List<XMLMethod> theMethods) {
		methods = theMethods;
	}

	@Override
	public String toString() {
		return "XMLClass [name=" + name + ",methods=" + methods + ",Checks="
				+ super.toString() + "]";
	}

}
