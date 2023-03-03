package sml.instruction;

import java.util.Objects;
import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

/**
 * This class ....
 * <p>
 * For multiplication operation
 *
 * @author ...
 */
public class MultiplyInstruction extends Instruction {
	private final RegisterName result;
	private final RegisterName source;

	/**
	 * The constant OP_CODE.
	 */
	public static final String OP_CODE = "mul";

	/**
	 * Instantiates a new Multiply instruction.
	 *
	 * @param label  the label
	 * @param result the result
	 * @param source the source
	 */
	public MultiplyInstruction(String label, RegisterName result, RegisterName source) {
		super(label, OP_CODE);
		this.result = result;
		this.source = source;
	}

	@Override
	public int execute(Machine m) {
		int value1 = m.getRegisters().get(result);
		int value2 = m.getRegisters().get(source);
		m.getRegisters().set(result, value1 * value2);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + result + " " + source;
	}

	@Override
	public int hashCode() {
		return Objects.hash(label, result, source);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof MultiplyInstruction other) {
			return Objects.equals(this.label, other.label)
					&& Objects.equals(this.result, other.result)
					&& Objects.equals(this.source, other.source);
		}
		return false;
	}
}
