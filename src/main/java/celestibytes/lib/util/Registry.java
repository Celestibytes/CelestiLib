package celestibytes.lib.util;

import java.util.HashMap;
import java.util.Map;

public class Registry<KEYTYPE, VALUE> {
	
	private static final String DEFAULT_REGISTRY_NAME = "unnamed_registry";
	
	Map<KEYTYPE, VALUE> registeredValues = null;
	VALUE nullValue = null;
	
	private String registryName = null;
	
	/** Constructs a new Registry with a name
	 *  @param registryName the name of the registry, if null the registry will be called "unnamed_registry"
	 */
	public Registry(String registryName) {
		registeredValues = new HashMap<KEYTYPE, VALUE>();
		if(registryName != null) {
			this.registryName = registryName;
		} else {
			this.registryName = DEFAULT_REGISTRY_NAME;
		}
	}
	
	/** Registers the value with the key if the key hasn't been registered already. */
	public void register(KEYTYPE key, VALUE value) {
		if(!registeredValues.containsKey(key)) {
			registeredValues.put(key, value);
		} else {
			System.err.println("Registry \"" + registryName + "\" already contains key \"" + key.toString() + "\", ignoring.");
		}
	}
	
	/** reRegisters the value with the key only if the key has already been registered (overwriting the old value). */
	public void reRegister(KEYTYPE key, VALUE value) {
		if(registeredValues.containsKey(key)) {
			registeredValues.put(key, value);
		}
	}
	
	/** Registers the value with the key without caring about if it has already been registered or not. */
	public void register_force(KEYTYPE key, VALUE value) {
		registeredValues.put(key, value);
	}
	
	/** Note: uses HashMap's clear method. */
	public void clear() {
		registeredValues.clear();
	}
	
	public boolean containsKey(KEYTYPE key) {
		return registeredValues.containsKey(key);
	}
	
	public boolean containsValue(VALUE value) {
		return registeredValues.containsValue(value);
	}
	
	/** Gets the value of the key, if nullValue is set that will be returned instead of null */
	public VALUE getValue(KEYTYPE key) {
		return registeredValues.containsKey(key) ? registeredValues.get(key) : this.nullValue;
	}
	
	/** Sets the nullValue which will be returned when trying to get an unregistered value */
	public void setNullValue(VALUE nullValue) {
		this.nullValue = nullValue;
	}
	
	/** Sets the nullValue to null, same as calling setNullValue with null */
	public void clearNullValue() {
		this.nullValue = null;
	}
}
