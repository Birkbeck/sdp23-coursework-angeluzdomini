package sml.instruction;

import static sml.Registers.Register.EAX;
import static sml.Registers.Register.EBX;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

class DivideInstructionTest {
  private Machine machine;
  private Registers registers;

  @BeforeEach
  void setUp() {
    machine = new Machine(new Registers());
    registers = machine.getRegisters();
    //...
  }

  @AfterEach
  void tearDown() {
    machine = null;
    registers = null;
  }

  @Test
  void executeValid() {
    registers.set(EAX, 6);
    registers.set(EBX, 3);
    Instruction instruction = new DivideInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(2, machine.getRegisters().get(EAX));
  }

  @Test
  void executeValidTwo() {
    registers.set(EAX, -6);
    registers.set(EBX, 3);
    Instruction instruction = new DivideInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(-2, machine.getRegisters().get(EAX));
  }
}