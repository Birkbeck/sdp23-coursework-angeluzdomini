package sml;

import java.util.Map;
import java.util.Optional;
import sml.instruction.AddInstruction;
import sml.instruction.DivideInstruction;
import sml.instruction.JumpIfNotZeroInstruction;
import sml.instruction.MultiplyInstruction;
import sml.instruction.PrintInstruction;
import sml.instruction.StoreInstruction;
import sml.instruction.SubtractInstruction;

/**
 * This class ...
 * <p>
 * Is a factory class to return the correct instruction class.
 *
 * @author ...
 */
public class InstructionFactory {

  /**
   * The Instruction map.
   */
  Map<String, Class<? extends Instruction>> instructionMap = Map.of(
      AddInstruction.OP_CODE, AddInstruction.class,
      SubtractInstruction.OP_CODE, SubtractInstruction.class,
      MultiplyInstruction.OP_CODE, MultiplyInstruction.class,
      DivideInstruction.OP_CODE, DivideInstruction.class,
      PrintInstruction.OP_CODE, PrintInstruction.class,
      StoreInstruction.OP_CODE, StoreInstruction.class,
      JumpIfNotZeroInstruction.OP_CODE, JumpIfNotZeroInstruction.class
  );

  /**
   * Gets instruction class.
   *
   * @param code the code
   * @return the instruction class
   */
  public Class<? extends Instruction> getInstructionClass(String code) {
    return Optional.ofNullable(instructionMap.get(code))
              .orElseThrow(() -> new RuntimeException("Error! No instruction class found for operation" + code + "."));
  }

}
