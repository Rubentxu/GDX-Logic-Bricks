package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.indignado.logicbricks.core.MessageHandler;


/**
 * @author Rubentxu.
 */
public class Property<V> {
    private String name;
    private V value;
    private boolean isObservable = false;


    public Property(String name, V value) {
        this(name, value, false);

    }


    public Property(String name, V value, boolean isObservable) {
        this.name = name;
        this.value = value;
        this.isObservable = isObservable;

    }


    public String getName() {
        return name;

    }


    public V getValue() {
        return value;

    }


    public void setValue(V value) {
        this.value = value;
        if (isObservable)
            MessageManager.getInstance().dispatchMessage(null, MessageHandler.getMessageKey(name + "_Changed"));

    }


    public boolean isObservable() {
        return isObservable;

    }


    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';

    }

}
