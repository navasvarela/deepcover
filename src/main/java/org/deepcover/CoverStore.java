package org.deepcover;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class CoverStore {

	private Map<String, ClassTracker> classes;
	private Paranamer paranamer;

	public CoverStore() {
		classes = new ConcurrentHashMap<String, ClassTracker>();
		paranamer = new CachingParanamer(new BytecodeReadingParanamer());
	}

	public void addClass(Class<?> cls) {

		Map<String, MethodTracker> methods = new ConcurrentHashMap<String, MethodTracker>();
		for (Method method : cls.getMethods()) {
			if (method.getDeclaringClass() == cls) {
				String[] parameterNames = paranamer.lookupParameterNames(method);
				MethodTracker m = new MethodTracker(method.getName(),
						parameterNames);
				methods.put(method.getName(), m);
			}
		}
		classes.put(cls.getName(), new ClassTracker(cls.getName(), methods));

	}

	@Override
	public String toString() {
		return "CoverStore [classes=" + classes + "]";
	}

}
