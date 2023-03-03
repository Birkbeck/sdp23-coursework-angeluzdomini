package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class ....
 * <p>
 * Manages Labels and addresses for instructions
 *
 * @author ...
 */
public final class Labels {
	private final Map<String, Integer> labels = new HashMap<>();

	/**
	 * Adds a label with the associated address to the map.
	 *
	 * @param label the label
	 * @param address the address the label refers to
	 */
	public void addLabel(String label, int address) {
		Objects.requireNonNull(label);
		if (labels.containsKey(label)) {
			throw new RuntimeException("Error! Label " + label + " already exists. Labels should be unique for each instruction.");
		}
		labels.put(label, address);
	}

	/**
	 * Returns the address associated with the label.
	 *
	 * @param label the label
	 * @return the address the label refers to
	 */
	public int getAddress(String label) {
		//Label should not be null. It can result in NullPointerException.
		if (label.isEmpty()) {
			throw new RuntimeException("Error! Label cannot be empty.");
		}
		return Optional.ofNullable(labels.get(label))
				           .orElseThrow(() -> new RuntimeException("Error! Label " + label + " does not exist."));
	}

	/**
	 * representation of this instance,
	 * in the form "[label -> address, label -> address, ..., label -> address]"
	 *
	 * @return the string representation of the labels map
	 */
	@Override
	public String toString() {
		return labels.entrySet().stream()
				.sorted(Map.Entry.comparingByValue())
				.map(e -> e.getKey() + " = " + e.getValue())
				.collect(Collectors.joining(", ", "[", "]"));
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Labels other) {
			return Objects.equals(this.labels, other.labels);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(labels);
	}

	/**
	 * Removes the labels
	 */
	public void reset() {
		labels.clear();
	}
}
