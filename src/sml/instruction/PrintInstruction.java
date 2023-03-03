package sml.instruction;

import java.util.Objects;
import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

/**
 * PrintInstruction class - For print/out operation
 *
 * @author Farsana Majeed Kallungal
 */
public class PrintInstruction extends Instruction {
	private final RegisterName source;

	/**
	 * The constant OP_CODE.
	 */
	public static final String OP_CODE = "out";

	/**
	 * Instantiates a new Print instruction.
	 *
	 * @param label  the label
	 * @param source the source
	 */
	public PrintInstruction(String label, RegisterName source) {
		super(label, OP_CODE);
		this.source = source;
	}

	@Override
	public int execute(Machine m) {
		int value = m.getRegisters().get(source);
		System.out.println(value);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + source;
	}

	@Override
	public int hashCode() {
		return Objects.hash(label, source);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof PrintInstruction other) {
			return Objects.equals(this.label, other.label)
					&& Objects.equals(this.source, other.source);
		}
		return false;
	}
}
