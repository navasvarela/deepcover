package org.deepcover;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.Type;

public final class CoverStore {
	private static final String COVERSTORE_SER = "coverstore.ser";
	private static final Log LOG = LogFactory.getLog(CoverStore.class);
	private static final Map<String, ClassTracker> classes;

	static {
		classes = new ConcurrentHashMap<String, ClassTracker>();

	}

	public static void addCheck(String className, String methodName,
			Object[] argumentValues) {
		LOG.info(String.format("addCheck(%s,%s,%s)", className, methodName,
				argumentValues));
		ClassTracker classTracker = classes.get(className);
		classTracker.calculateChecksForMethod(methodName, argumentValues);
		serialize();

	}

	public static void add(String className, String methodName,
			String methodSignature) {
		LOG.info(String.format("add(%s,%s,%s)", className, methodName,
				methodSignature));
		if (!classes.containsKey(className)) {
			classes.put(className, new ClassTracker(className));
		}

		ClassTracker classTracker = classes.get(className);
		// Do not add method with void arguments.
		if (!methodSignature.contains("()"))
			classTracker.addMethod(methodName, parseSignature(methodSignature));

		serialize();
	}

	public static String print() {
		return "CoverStore [classes=" + classes + "]";
	}

	public static ArgTypes[] parseSignature(String methodSignature) {
		LOG.debug("parsing signature: " + methodSignature);
		Type[] types = Type.getArgumentTypes(methodSignature);
		ArgTypes[] result = new ArgTypes[types.length];
		for (int i = 0; i < types.length; i++) {
			result[i] = parseType(types[i]);
		}

		return result;
	}

	protected static ArgTypes parseType(Type type) {
		ArgTypes result = null;
		if (type.getClassName().endsWith("java.lang.String")) {
			result = ArgTypes.STRING;

		} else if (type.getSort() == Type.ARRAY
				|| (type.getSort() == Type.OBJECT && !isANumberObject(type))
				|| type.getSort() == Type.BOOLEAN) {
			// is a number
			result = ArgTypes.OBJECT;
		} else
			result = ArgTypes.NUMBER;
		return result;
	}

	private static boolean isANumberObject(Type type) {
		String className = type.getClassName();
		try {
			return Number.class.isAssignableFrom(Class.forName(className));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private static void serialize() {
		LOG.info("serialize -" + classes);
		try {
			File file = new File(COVERSTORE_SER);
			if (!file.exists())
				file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeObject(classes);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Map<String, ClassTracker> deserialize() {
		try {
			File file = new File(COVERSTORE_SER);
			if (file.exists()) {

				FileInputStream fis = new FileInputStream(COVERSTORE_SER);
				ObjectInputStream ois = new ObjectInputStream(fis);
				return (Map<String, ClassTracker>) ois.readObject();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, ClassTracker> getStore() {

		return deserialize();
	}
}
