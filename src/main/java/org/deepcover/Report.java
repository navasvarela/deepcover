package org.deepcover;

import java.io.File;
import java.io.FilenameFilter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.deepcover.report.ReportModel;
import org.deepcover.report.ReportWriter;
import org.deepcover.report.XMLArgument;
import org.deepcover.report.XMLClass;
import org.deepcover.report.XMLMethod;
import org.deepcover.report.XMLPackage;
import org.deepcover.report.XMLReportDocument;
import org.deepcover.report.XMLSourceElement;

public class Report {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// 1. Find all deepcover-XXXX.xml in current folder
		File[] reportFiles = findReportFiles();
		System.err.println("Found " + reportFiles.length + " files");
		JAXBContext context = JAXBContext.newInstance(XMLArgument.class,
				XMLReportDocument.class, XMLSourceElement.class,
				XMLClass.class, XMLMethod.class, XMLPackage.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		// 2. Compile BIG XmlReport
		ReportModel report = new ReportModel();
		for (File file : reportFiles) {
			report.accummulate((XMLReportDocument) unmarshaller.unmarshal(file));
		}
		// 3. Write XML
		ReportWriter writer = new ReportWriter("deepcover.xml");
		writer.writeXml(report);
		// 4. Write HTML
		writer.writeHtml("deepcover.html", report);
		// 5. Delete individual files.
		for (File file : reportFiles) {
			file.delete();
		}

	}

	public static File[] findReportFiles() {
		File dir = new File(".");
		File[] files = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File file, String name) {

				return name.matches("deepcover.*xml");
			}
		});
		return files;
	}

}
