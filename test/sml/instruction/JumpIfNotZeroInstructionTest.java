package sml.instruction;

import static sml.Registers.Register.EAX;
import static sml.Registers.Register.EBX;
import static sml.Registers.Register.ECX;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Labels;
import sml.Machine;
import sml.Registers;

class JumpIfNotZeroInstructionTest {
  private Machine machine;
  private Registers registers;
  private Labels labels;
  private List<Instruction> program;

  @BeforeEach
  void setUp() {
    machine = new Machine(new Registers());
    registers = machine.getRegisters();
    labels = machine.getLabels();
    program = machine.getProgram();
  }

  @AfterEach
  void tearDown() {
    machine = null;
    registers = null;
    labels = null;
    program = null;
  }

  @Test
  void executeValid() {
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    registers.set(ECX, 2);

    labels.addLabel("test", 0);
    Instruction addInstruction = new AddInstruction("test", EBX, ECX);
    program.add(addInstruction);

    Instruction instruction = new JumpIfNotZeroInstruction(null, EAX, "test");
    instruction.execute(machine);
    Assertions.assertEquals(8, machine.getRegisters().get(EBX));
  }

  @Test
  void executeValid2() {
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    registers.set(ECX, 2);

    labels.addLabel("test1", 0);
    labels.addLabel("test2", 1);

    Instruction jnzInstruction = new JumpIfNotZeroInstruction("test1", EBX, "test2");
    Instruction addInstruction = new AddInstruction("test2", ECX, EAX);
    program.add(jnzInstruction);
    program.add(addInstruction);

    Instruction instruction = new JumpIfNotZeroInstruction(null, EAX, "test1");
    instruction.execute(machine);
    Assertions.assertEquals(7, machine.getRegisters().get(ECX));
  }

  @Test
  void executeInvalid() {
    registers.set(EAX, 0);
    registers.set(EBX, 6);
    registers.set(ECX, 2);

    labels.addLabel("test", 0);
    Instruction addInstruction = new AddInstruction("test", EBX, ECX);
    program.add(addInstruction);

    Instruction instruction = new JumpIfNotZeroInstruction(null, EAX, "test");
    instruction.execute(machine);
    Assertions.assertEquals(6, machine.getRegisters().get(EBX));
  }

}