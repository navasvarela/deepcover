package org.deepcover;

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

public class DCClassFileTransformer implements ClassFileTransformer, Opcodes {

	private static final Log LOG = LogFactory.getLog(DCClassFileTransformer.class);
	private final CoverStore store;
	private final String parentPackage;

	public DCClassFileTransformer(String theParentPackage) {
		store = new CoverStore();
		parentPackage = theParentPackage;
	}

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		if (classBeingRedefined.getPackage().getName().contains(parentPackage)) {
			LOG.debug("Transforming class: "+classBeingRedefined.getName());
			ClassReader cr = new ClassReader(classfileBuffer);
			ClassWriter cw = new ClassWriter(0);
			ClassVisitor cv = new ClassVisitor(ASM4, cw) {

				@Override
				public MethodVisitor visitMethod(int theAccess, String theName,
						String theDesc, String theSignature,
						String[] theExceptions) {
					// TODO Auto-generated method stub
					return super.visitMethod(theAccess, theName, theDesc, theSignature,
							theExceptions);
				}
				
				
			};
			cr.accept(cv, 0);
			
		}
		return classfileBuffer;
	}

}
