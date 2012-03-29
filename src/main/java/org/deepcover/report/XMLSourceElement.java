package org.deepcover.report;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class XMLSourceElement {

	@XmlElement
	private String nullChecks;
	@XmlElement
	private String emptyChecks;
	@XmlElement
	private String notNullChecks;

	public String getNullChecks() {
		return nullChecks;
	}

	public void setNullChecks(String theNullChecks) {
		nullChecks = theNullChecks;
	}

	public String getEmptyChecks() {
		return emptyChecks;
	}

	public void setEmptyChecks(String theEmptyChecks) {
		emptyChecks = theEmptyChecks;
	}

	public String getNotNullChecks() {
		return notNullChecks;
	}

	public void setNotNullChecks(String theNotNullChecks) {
		notNullChecks = theNotNullChecks;
	}

	@Override
	public String toString() {
		return "[nullChecks=" + nullChecks + ", emptyChecks=" + emptyChecks
				+ ", notNullChecks=" + notNullChecks + "]";
	}

}
