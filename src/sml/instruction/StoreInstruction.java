package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

// TODO: write a JavaDoc for the class

/**
 * @author
 */

public class StoreInstruction extends Instruction {
	private final RegisterName registerName;
	private final int registerValue;

	public static final String OP_CODE = "mov";

	public StoreInstruction(String label, RegisterName registerName, int registerValue) {
		super(label, OP_CODE);
		this.registerName = registerName;
		this.registerValue = registerValue;
	}

	@Override
	public int execute(Machine m) {
		m.getRegisters().set(registerName, registerValue);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + registerName + " " + registerValue;
	}

	/**
	 * @return
	 */
	@Override
	public int hashCode() {
		return 0;
	}

	/**
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		return false;
	}
}
