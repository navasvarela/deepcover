package org.deepcover.report;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.deepcover.ClassTracker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ReportWriter {

	private final String fileName;

	private Map<String, List<ClassTracker>> storeByPackage;

	private final Map<String, ClassTracker> store;

	private XmlReport report;

	public ReportWriter(String theFileName, Map<String, ClassTracker> theStore) {

		fileName = theFileName;
		store = theStore;
		createStoreByPackage();
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
	}

	private XmlReport createXmlReport() {
		XmlReport result = new XmlReport();
		for (String packageName : storeByPackage.keySet()) {
			PackageElement pkg = new PackageElement();
			pkg.setName(packageName);
			pkg.addClasses(storeByPackage.get(packageName));
			result.addPackage(pkg);

		}
		return result;
	}

	protected static String getPackageName(ClassTracker tracker) {
		String className = tracker.getClassName().replaceAll("/", ".");
		String packageName = className.substring(0, className.lastIndexOf('.'));
		return packageName;
	}

	public void writeXml() {
		Configuration conf = new Configuration();
		conf.setClassForTemplateLoading(ReportWriter.class, "/");
		report = createXmlReport();
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
		Configuration conf = new Configuration();
		conf.setClassForTemplateLoading(ReportWriter.class, "/");
		if (report == null)
			report = createXmlReport();

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
