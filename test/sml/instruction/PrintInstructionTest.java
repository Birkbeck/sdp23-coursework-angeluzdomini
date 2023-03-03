package sml.instruction;

import static sml.Registers.Register.EAX;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

class PrintInstructionTest {
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
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    registers.set(EAX, 5);

    Instruction instruction = new PrintInstruction(null, EAX);
    instruction.execute(machine);

    Assertions.assertEquals("5\n", outContent.toString());
    Assertions.assertEquals(5, machine.getRegisters().get(EAX));
  }
}