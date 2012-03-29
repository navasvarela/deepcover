package org.deepcover.report;

import javax.xml.bind.annotation.XmlAttribute;

public class XMLArgument extends XMLSourceElement {

	@XmlAttribute
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String theName) {
		name = theName;
	}

	@Override
	public String toString() {
		return "\t\t\nXMLArgument [name=" + name + ", checks="
				+ super.toString() + "]";
	}
}
