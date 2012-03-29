package org.deepcover.report;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.deepcover.ClassTracker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ReportWriter {
	private static final Log LOG = LogFactory.getLog(ReportWriter.class);

	private final String fileName;

	private Map<String, List<ClassTracker>> storeByPackage;

	private Map<String, ClassTracker> store;

	private ReportModel report;

	public ReportWriter(String theFileName, Map<String, ClassTracker> theStore) {
		this(theFileName);
		LOG.debug("ReportWriter(" + theFileName + "," + theStore + ")");
		store = theStore;
		createStoreByPackage();
	}

	public ReportWriter(String theFileName) {
		fileName = theFileName;

	}

	private void createStoreByPackage() {

		storeByPackage = new HashMap<String, List<ClassTracker>>();
		for (ClassTracker tracker : store.values()) {
			String packageName = getPackageName(tracker);
			List<ClassTracker> classesInPackage = storeByPackage
					.get(packageName);
			if (classesInPackage == null) {
				classesInPackage = new ArrayList<ClassTracker>();
			}
			classesInPackage.add(tracker);
			storeByPackage.put(packageName, classesInPackage);
		}
		LOG.debug("Created storeByPackage: " + storeByPackage);

	}

	private ReportModel createXmlReport() {
		ReportModel result = new ReportModel();
		for (String packageName : storeByPackage.keySet()) {
			PackageElement pkg = new PackageElement();
			pkg.setName(packageName);
			pkg.addClasses(storeByPackage.get(packageName));
			result.addPackage(pkg);

		}
		LOG.debug("Created XMLReport: " + result);
		return result;
	}

	protected static String getPackageName(ClassTracker tracker) {
		String className = tracker.getClassName().replaceAll("/", ".");
		String packageName = className.substring(0, className.lastIndexOf('.'));
		return packageName;
	}

	public void writeXml() {
		writeXml(createXmlReport());

	}

	public void writeXml(ReportModel report) {
		LOG.debug("Writing XML For Report: " + report);
		Configuration conf = new Configuration();
		conf.setClassForTemplateLoading(ReportWriter.class, "/");

		try {
			Template tmpl = conf.getTemplate("/template.xml");
			tmpl.process(report, new FileWriter(fileName));
		} catch (IOException e) {

			e.printStackTrace();
		} catch (TemplateException e) {

			e.printStackTrace();
		}
	}

	public void writeHtml(String fileName) {
		if (report == null)
			report = createXmlReport();
		writeHtml(fileName, report);

	}

	public void writeHtml(String fileName, ReportModel report) {
		Configuration conf = new Configuration();
		conf.setClassForTemplateLoading(ReportWriter.class, "/");

		try {
			Template tmpl = conf.getTemplate("/base_template.html");
			tmpl.process(report, new FileWriter(fileName));
		} catch (IOException e) {

			e.printStackTrace();
		} catch (TemplateException e) {

			e.printStackTrace();
		}

	}

}
