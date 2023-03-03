package sml.instruction;

import java.util.List;
import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

// TODO: write a JavaDoc for the class

/**
 * @author
 */

public class JumpIfNotZeroInstruction extends Instruction {

	private final RegisterName source;

	private final String nextLabel;

	public static final String OP_CODE = "jnz";

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
