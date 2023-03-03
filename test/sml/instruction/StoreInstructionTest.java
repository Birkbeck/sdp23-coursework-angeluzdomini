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

class StoreInstructionTest {
  private Machine machine;

  @BeforeEach
  void setUp() {
    machine = new Machine(new Registers());
    //...
  }

  @AfterEach
  void tearDown() {
    machine = null;
  }

  @Test
  void executeValid() {
    Instruction instruction = new StoreInstruction(null, EAX, 6);
    instruction.execute(machine);
    Assertions.assertEquals(6, machine.getRegisters().get(EAX));
  }
}