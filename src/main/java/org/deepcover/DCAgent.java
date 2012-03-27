package org.deepcover;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.instrument.Instrumentation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.deepcover.report.ReportWriter;

public class DCAgent {
	private static final String[] RESOURCES = new String[] {
			"bootstrap/css/bootstrap.css",
			"bootstrap/css/bootstrap-responsive.css",
			"bootstrap/js/bootstrap.js", "jquery-1.7.2.min.js" };

	private static final Log LOG = LogFactory.getLog(DCAgent.class);

	private static Instrumentation instrumentation;

	private static final String parentPkg = System
			.getProperty("org.deepcover.pkg");

	/**
	 * JVM hook to statically load the javaagent at startup.
	 * 
	 * After the Java Virtual Machine (JVM) has initialized, the premain method
	 * will be called. Then the real application main method will be called.
	 * 
	 * @param args
	 * @param inst
	 * @throws Exception
	 */
	public static void premain(String args, Instrumentation inst)
			throws Exception {
		LOG.info(String
				.format("premain method invoked with args: %s and inst: %s",
						args, inst));
		instrumentation = inst;
		if (parentPkg == null)
			throw new RuntimeException("Missing property: org.deepcover.pkg");
		LOG.info("Using package: " + parentPkg);
		instrumentation.addTransformer(new DCClassFileTransformer(parentPkg),
				true);

		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				System.err.println("CoverStore dump: ");
				System.err.println(CoverStore.print());
				unzipResources();
				ReportWriter writer = new ReportWriter("deepcover.xml",
						CoverStore.getStore());
				writer.writeXml();
				writer.writeHtml("deepcover.html");
			}

		});
	}

	private static void unzipResources() {

		File css = new File("bootstrap/css");
		File js = new File("bootstrap/js");

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
			writer = new BufferedWriter(new FileWriter(resource));
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
