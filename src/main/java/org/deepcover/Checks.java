package org.deepcover;

public enum Checks {
	
	NULL(1), ZERO_OR_EMPTY(2), NOT_NULL(4);
	
	private int mask;
	
	private Checks(int theMask){
		this.mask = theMask;
	}

	public int getMask() {
		return mask;
	}

}
