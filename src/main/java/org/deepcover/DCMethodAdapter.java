package org.deepcover;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

public class DCMethodAdapter extends AdviceAdapter {

	private static final Log LOG = LogFactory.getLog(DCMethodAdapter.class);

	private String methodName;
	private String className;

	protected DCMethodAdapter(int api, MethodVisitor mv, int access,
			String name, String desc, String clsName) {
		super(api, mv, access, name, desc);
		methodName = name;
		className = clsName;

	}

	@Override
	protected void onMethodEnter() {
		LOG.debug("onMethodEnter(), " + methodName);
		try {
			visitLdcInsn(methodName);
			int methodIndex = newLocal(Type.getType(String.class));
			storeLocal(methodIndex);
			visitLdcInsn(className);
			int clsIndex = newLocal(Type.getType(String.class));
			storeLocal(clsIndex);
			loadLocal(clsIndex);
			loadLocal(methodIndex);
			loadArgArray();
			visitMethodInsn(INVOKESTATIC, "org/deepcover/CoverStore",
					"addCheck", Type.getMethodDescriptor(CoverStore.class
							.getMethod("addCheck", String.class, String.class,
									Object[].class)));
		} catch (Throwable t) {
			LOG.error("While visiting method " + methodName + " in class "
					+ className, t);

		}

	}
}
