package com.indignado.logicbricks.data;

/**
 * @author Rubentxu.
 */
public class Property<T> {
    public enum Mode { Assign, Add, Toggle, Copy }

    public Mode mode;
    public String name;
    public T value;


    public Property(Mode mode, String name, T value) {
        this.mode = mode;
        this.name = name;
        this.value = value;
    }

}
