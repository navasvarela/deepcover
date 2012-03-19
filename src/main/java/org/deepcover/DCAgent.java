package org.deepcover;

import java.lang.instrument.Instrumentation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DCAgent {
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
			}

		});
	}
}
