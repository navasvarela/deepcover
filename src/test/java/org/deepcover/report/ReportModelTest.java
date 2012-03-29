package org.deepcover.report;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.BeforeClass;
import org.junit.Test;

public class ReportModelTest {

	private static JAXBContext context;

	@BeforeClass
	public static void setup() throws Exception {
		context = JAXBContext.newInstance(XMLArgument.class,
				XMLReportDocument.class, XMLSourceElement.class,
				XMLClass.class, XMLMethod.class, XMLPackage.class);
	}

	@Test
	public void shouldDoRoundTripWithOneClass() throws Exception {
		// setup
		Unmarshaller unmarshaller = context.createUnmarshaller();
		// parse xml file
		URL deepCoverUrl = this.getClass().getResource("/deepcover.xml");
		XMLReportDocument doc = (XMLReportDocument) unmarshaller
				.unmarshal(deepCoverUrl);

		// put into a ReportModel
		ReportModel model = new ReportModel();
		model.accummulate(doc);

		// write to file
		String roundtripFilename = "build/deepcover-roundtrip.xml";
		ReportWriter writer = new ReportWriter(roundtripFilename);
		writer.writeXml(model);

		// compareLineByLine

		compareLineByLine(new File(deepCoverUrl.getFile()), new File(
				roundtripFilename));

	}

	@Test
	public void shouldMergeTwoReports() throws Exception {
		// setup
		Unmarshaller unmarshaller = context.createUnmarshaller();
		// parse xml file
		URL deepCoverUrl = this.getClass().getResource("/deepcover.xml");
		URL deepCoverUrl2 = this.getClass().getResource("/deepcover_2.xml");
		XMLReportDocument doc = (XMLReportDocument) unmarshaller
				.unmarshal(deepCoverUrl);
		XMLReportDocument doc2 = (XMLReportDocument) unmarshaller
				.unmarshal(deepCoverUrl2);

		// put into a ReportModel
		ReportModel model = new ReportModel();
		model.accummulate(doc);
		model.accummulate(doc2);

		// write to file
		String mergedFilename = "build/deepcover-merged2.xml";
		ReportWriter writer = new ReportWriter(mergedFilename);
		writer.writeXml(model);

		// ASSERT
		XMLReportDocument reportDoc = (XMLReportDocument) unmarshaller
				.unmarshal(new File("build/deepcover-merged2.xml"));
		assertEquals(1, reportDoc.getPackages().size());
	}

	public static void compareLineByLine(File a, File b) throws Exception {

		BufferedReader aReader = new BufferedReader(new FileReader(a));
		BufferedReader bReader = new BufferedReader(new FileReader(b));
		String aline = null;
		String bline = null;
		int lineCounter = 0;
		while ((aline = aReader.readLine()) != null) {
			lineCounter++;
			bline = bReader.readLine();
			assertEquals("In line " + lineCounter, aline, bline);
		}

	}

}
