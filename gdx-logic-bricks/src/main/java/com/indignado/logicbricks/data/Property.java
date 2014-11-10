package com.indignado.logicbricks.data;

import com.badlogic.ashley.core.Component;

import java.util.Properties;

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
