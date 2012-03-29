package org.deepcover.report;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

public class XMLReportDocumentTest {

	@Test
	public void shouldBeAbleToParseReport() throws Exception {
		JAXBContext context = JAXBContext.newInstance(XMLArgument.class,
				XMLReportDocument.class, XMLSourceElement.class,
				XMLClass.class, XMLMethod.class, XMLPackage.class);

		Unmarshaller unmarshaller = context.createUnmarshaller();
		XMLReportDocument doc = (XMLReportDocument) unmarshaller.unmarshal(this
				.getClass().getResource("/deepcover.xml"));
		System.err.println(doc.toString());
		assertEquals("0 / 3", doc.getNullChecks());

		assertEquals(1, doc.getPackages().size());
		XMLPackage pkg = doc.getPackages().get(0);
		assertEquals("0 / 3", pkg.getNullChecks());
		assertEquals(1, pkg.getClasses().size());
		XMLClass cls = pkg.getClasses().get(0);
		assertEquals("0 / 3", pkg.getNullChecks());

	}
}
