package org.deepcover.report;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "method")
public class XMLMethod extends XMLSourceElement {

	@XmlElement(name = "argument")
	private List<XMLArgument> arguments;

	@XmlAttribute
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String theName) {
		name = theName;
	}

	public List<XMLArgument> getArguments() {
		return arguments;
	}

	public void setArguments(List<XMLArgument> theArguments) {
		arguments = theArguments;
	}

	@Override
	public String toString() {
		return "\t\nXMLMethod [arguments=" + arguments + ", name=" + name
				+ ", checks=" + super.toString() + "]";
	}

}
