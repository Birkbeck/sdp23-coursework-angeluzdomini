package sml;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Registers - stores and manages different registers and values
 * for the described enum Register.
 *
 * @author ...
 */
public final class Registers {
    private final Map<Register, Integer> registers = new HashMap<>();

    /**
     * The enum Register.
     */
    public enum Register implements RegisterName {
        EAX, EBX, ECX, EDX, ESP, EBP, ESI, EDI;
    }

    /**
     * Instantiates a new Registers.
     */
    public Registers() {
        clear(); // the class is final
    }

    /**
     * Clear.
     */
    public void clear() {
        for (Register register : Register.values())
            registers.put(register, 0);
    }

    /**
     * Sets the given register to the value.
     *
     * @param register register name
     * @param value    new value
     */
    public void set(RegisterName register, int value) {
        registers.put((Register)register, value);
    }

    /**
     * Returns the value stored in the register.
     *
     * @param register register name
     * @return value int
     */
    public int get(RegisterName register) {
        return registers.get((Register)register);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Registers other) {
            return registers.equals(other.registers);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return registers.hashCode();
    }

    @Override
    public String toString() {
        return registers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining(", ", "[", "]")) ;
    }
}
