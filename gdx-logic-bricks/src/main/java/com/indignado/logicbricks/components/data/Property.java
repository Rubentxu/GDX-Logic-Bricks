package com.indignado.logicbricks.components.data;

/**
 * @author Rubentxu.
 */
public class Property<V> {
    public String name;
    public V value;

    public Property(String name, V value) {
        this.name = name;
        this.value = value;

    }

}
