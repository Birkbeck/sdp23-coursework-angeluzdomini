package sml.instruction;

import java.util.List;
import java.util.Objects;
import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

/**
 * JumpIfNotZeroInstruction class - For jump if not zero operation
 *
 * @author Farsana Majeed Kallungal
 */
public class JumpIfNotZeroInstruction extends Instruction {

	private final RegisterName source;

	private final String nextLabel;

	/**
	 * The constant OP_CODE.
	 */
	public static final String OP_CODE = "jnz";

	/**
	 * Instantiates a new Jump if not zero instruction.
	 *
	 * @param label     the label
	 * @param source    the source
	 * @param nextLabel the next label
	 */
	public JumpIfNotZeroInstruction(String label, RegisterName source, String nextLabel) {
		super(label, OP_CODE);
		this.source = source;
		this.nextLabel = nextLabel;
	}

	@Override
	public int execute(Machine m) {
		int value = m.getRegisters().get(source);
		if (value != 0) {
			executeLabelledInstruction(m);
		}
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	private void executeLabelledInstruction(Machine m) {
		int address = m.getLabels().getAddress(nextLabel);
		List<Instruction> program = m.getProgram();
		Instruction ins = program.get(address);
		ins.execute(m);
	}

	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + source + " " + nextLabel;
	}

	public int hashCode() {
		return Objects.hash(label, source, nextLabel);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof JumpIfNotZeroInstruction other) {
			return Objects.equals(this.label, other.label)
					&& Objects.equals(this.source, other.source)
					&& Objects.equals(this.nextLabel, other.nextLabel);
		}
		return false;
	}
}
