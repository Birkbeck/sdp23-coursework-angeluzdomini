package sml.instruction;

import java.util.Objects;
import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

/**
 * This class ....
 * <p>
 * For store/move operation
 *
 * @author ...
 */
public class StoreInstruction extends Instruction {
	private final RegisterName registerName;
	private final int registerValue;

	/**
	 * The constant OP_CODE.
	 */
	public static final String OP_CODE = "mov";

	/**
	 * Instantiates a new Store instruction.
	 *
	 * @param label         the label
	 * @param registerName  the register name
	 * @param registerValue the register value
	 */
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

	@Override
	public int hashCode() {
		return Objects.hash(label, registerName, registerValue);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof StoreInstruction other) {
			return Objects.equals(this.label, other.label)
					&& Objects.equals(this.registerName, other.registerName)
					&& Objects.equals(this.registerValue, other.registerValue);
		}
		return false;
	}
}
