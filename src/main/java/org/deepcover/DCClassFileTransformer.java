package org.deepcover;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.TraceClassVisitor;

public class DCClassFileTransformer implements ClassFileTransformer, Opcodes {

	private static final Log LOG = LogFactory
			.getLog(DCClassFileTransformer.class);

	private final String parentDir;

	private final boolean dump = System.getProperty("org.deepcover.dump") != null;

	private Set<String> transformedClasses;

	public DCClassFileTransformer(String theParentPackage) {
		parentDir = theParentPackage.replaceAll("\\.", "/");
		LOG.info("Class file transformer using dir: " + parentDir);
		transformedClasses = new HashSet<String>();
	}

	@Override
	public byte[] transform(ClassLoader loader, final String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {

		if (className.contains(parentDir) && !className.endsWith("Test")
				&& !className.contains("Test$")
				&& !transformedClasses.contains(className)) {
			LOG.debug("Transforming class: " + className);

			ClassReader cr = new ClassReader(classfileBuffer);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			try {
				ClassVisitor cv = new ClassVisitor(ASM4, cw) {

					@Override
					public MethodVisitor visitMethod(int access,
							String methodName, String desc, String signature,
							String[] theExceptions) {

						LOG.debug(String.format("visitMethod(%d,%s,%s,%s,%s)",
								access, methodName, desc, signature,
								theExceptions));
						MethodVisitor mv = super.visitMethod(access,
								methodName, desc, signature, theExceptions);
						if (mv == null)
							LOG.debug("MethodVisitor null for method: "
									+ methodName);
						if (mv != null && !"<init>".equals(methodName)) {
							if (ACC_PUBLIC == access && !desc.contains("()")) {
								CoverStore.add(className, methodName, desc);
								mv = new DCMethodAdapter(ASM4, mv, access,
										methodName, desc, className);
							}
						}
						return mv;

					}

				};
				cr.accept(cv, ClassReader.EXPAND_FRAMES
						+ ClassReader.SKIP_DEBUG);

				LOG.info("Dumping bytecode for class:" + className);
				dump(cw.toByteArray(), className);

			} catch (Throwable t) {
				LOG.error("Exception while writing class", t);
			}
			transformedClasses.add(className);
			return cw.toByteArray();
		}
		return classfileBuffer;
	}

	private static void dump(byte[] bytes, String classname) throws IOException {
		LOG.debug("dump - bytes: " + bytes.length + ", class: " + classname);
		ClassReader reader = new ClassReader(bytes);
		File deepcoverDir = new File("deepcover");
		if (!deepcoverDir.exists())
			deepcoverDir.mkdir();
		String fileName = "deepcover/" + classname.replaceAll("/", ".")
				+ ".dmp";
		File dumpFile = new File(fileName);
		dumpFile.createNewFile();
		ClassVisitor visitor = new TraceClassVisitor(new PrintWriter(
				new FileWriter(fileName)));
		reader.accept(visitor, 0);

	}
}
