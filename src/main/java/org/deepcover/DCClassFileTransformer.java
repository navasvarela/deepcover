package org.deepcover;

import java.io.PrintWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

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

	private final String parentPackage;

	public DCClassFileTransformer(String theParentPackage) {
		parentPackage = theParentPackage;
	}

	@Override
	public byte[] transform(ClassLoader loader, final String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {

		if (className.contains(parentPackage) && !className.endsWith("Test")) {
			LOG.debug("Transforming class: " + className);

			ClassReader cr = new ClassReader(classfileBuffer);
			ClassWriter cw = new ClassWriter(1);
			ClassVisitor cv = new ClassVisitor(ASM4, cw) {

				@Override
				public MethodVisitor visitMethod(int access, String methodName,
						String desc, String signature, String[] theExceptions) {
					// TODO Auto-generated method stub
					LOG.info(String.format("visitMethod(%d,%s,%s,%s,%s)",
							access, methodName, desc, signature, theExceptions));
					CoverStore.add(className, methodName, desc);
					MethodVisitor mv = super.visitMethod(access, methodName,
							desc, signature, theExceptions);
					LOG.info("MV: " + mv);
					if (mv != null) {
						if (ACC_PUBLIC == access && !desc.contains("()")) {
							mv = new DCMethodAdapter(ASM4, mv, access,
									methodName, desc, className);
						}
					}
					return mv;

				}

			};
			try {
				cr.accept(cv, ClassReader.EXPAND_FRAMES);
			} catch (Throwable t) {
				LOG.error(t);
			}
			LOG.info("Dumping bytecode for class:" + className);
			dump(cw.toByteArray());
			return cw.toByteArray();
		}
		return classfileBuffer;
	}

	private static void dump(byte[] bytes) {
		ClassReader reader = new ClassReader(bytes);
		ClassVisitor visitor = new TraceClassVisitor(
				new PrintWriter(System.out));
		reader.accept(visitor, 0);
	}

}
