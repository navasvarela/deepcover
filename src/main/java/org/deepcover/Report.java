package org.deepcover;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	private static final String[] RESOURCES = new String[] {
			"bootstrap/css/bootstrap.css",
			"bootstrap/css/bootstrap-responsive.css",
			"bootstrap/js/bootstrap.js", "jquery-1.7.2.min.js" };

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
		ReportWriter writer = new ReportWriter("deepcover/deepcover.xml");
		writer.writeXml(report);
		// 4. Write HTML
		writer.writeHtml("deepcover/deepcover.html", report);
		// 5. Delete individual files.
		for (File file : reportFiles) {
			file.delete();
		}
		unzipResources();

	}

	public static File[] findReportFiles() {
		File dir = new File("./deepcover");
		File[] files = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File file, String name) {

				return name.matches("deepcover.*xml");
			}
		});
		return files;
	}

	private static void unzipResources() {

		File css = new File("deepcover/bootstrap/css");
		File js = new File("deepcover/bootstrap/js");

		css.mkdirs();
		js.mkdirs();
		for (String resource : RESOURCES) {
			new File(resource).delete();

			copyFile(resource);
		}

	}

	protected static void copyFile(String resource) {

		InputStream in = DCAgent.class.getResourceAsStream("/" + resource);
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			writer = new BufferedWriter(new FileWriter("deepcover/" + resource));
			String s;
			while ((s = reader.readLine()) != null) {
				writer.append(s + System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (writer != null) {
				try {

					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

}
