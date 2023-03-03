package sml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import sml.instruction.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static sml.Registers.Register;

/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author ...
 */
public final class Translator {

    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    Map<String, Class<? extends Instruction>> instructionMap = Map.of(
        AddInstruction.OP_CODE, AddInstruction.class,
        SubtractInstruction.OP_CODE, SubtractInstruction.class,
        MultiplyInstruction.OP_CODE, MultiplyInstruction.class,
        DivideInstruction.OP_CODE, DivideInstruction.class,
        PrintInstruction.OP_CODE, PrintInstruction.class,
        StoreInstruction.OP_CODE, StoreInstruction.class,
        JumpIfNotZeroInstruction.OP_CODE, JumpIfNotZeroInstruction.class
    );

    public Translator(String fileName) {
        this.fileName =  fileName;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException {
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();

            // Each iteration processes line and reads the next input line into "line"
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();

                Instruction instruction = getInstruction(label);
                if (instruction != null) {
                    if (label != null)
                        labels.addLabel(label, program.size());
                    program.add(instruction);
                }
            }
        }
    }

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label) {
        if (line.isEmpty()) {
            return null;
        }

        String opcode = scan();
        try {
            Class<? extends Instruction> instructionClazz = instructionMap.get(opcode);
            Constructor<?>[] constructors = instructionClazz.getDeclaredConstructors();
            if (constructors.length != 1) {
                System.err.println(
                    "Unexpected number of constructors for class: " + instructionClazz.getName());
                return null;
            }
            Constructor<?> candidateConstructor = constructors[0];
            int parameterCount = candidateConstructor.getParameterCount();
            List<Object> args = new ArrayList<>(parameterCount);
            args.add(label);
            for (int i = 1; i < parameterCount; i++) {
                args.add(scan());
            }
            Object[] parameterObjs = new Object[parameterCount];
            Class<?>[] paramCons = candidateConstructor.getParameterTypes();
            for (int i = 0; i < parameterCount; i++) {
                if (args.get(i) == null) {
                    parameterObjs[i] = null;
                } else if (paramCons[i].getName().equals(RegisterName.class.getName())) {
                    parameterObjs[i] = Register.valueOf((String) args.get(i));
                } else {
                    Class<?> c = toWrapper(paramCons[i]);
                    parameterObjs[i] = c.getConstructor(String.class).newInstance(args.get(i));
                }
            }
            return (Instruction) candidateConstructor.newInstance(parameterObjs);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    private String getLabel() {
        String word = scan();
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /*
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     */
    private String scan() {
        line = line.trim();

        for (int i = 0; i < line.length(); i++)
            if (Character.isWhitespace(line.charAt(i))) {
                String word = line.substring(0, i);
                line = line.substring(i);
                return word;
            }

        return line;
    }

    private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_WRAPPERS = Map.of(
        int.class, Integer.class,
        long.class, Long.class,
        boolean.class, Boolean.class,
        byte.class, Byte.class,
        char.class, Character.class,
        float.class, Float.class,
        double.class, Double.class,
        short.class, Short.class,
        void.class, Void.class);

    /**
     * Return the correct Wrapper class
     *
     * @param paramClass
     * @return Object class
     */
    private static Class<?> toWrapper(Class<?> paramClass) {
        return PRIMITIVE_TYPE_WRAPPERS.getOrDefault(paramClass, paramClass);
    }
}