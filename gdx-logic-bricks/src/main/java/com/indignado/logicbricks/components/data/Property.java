package com.indignado.logicbricks.components.data;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.indignado.logicbricks.core.MessageManager;

/**
 * @author Rubentxu.
 */
public class Property<V> {
    private String name;
    private V value;
    private boolean isObservable = false;

    public Property(String name, V value) {
        this.name = name;
        this.value = value;

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
            MessageDispatcher.getInstance().dispatchMessage(null, MessageManager.getMessageKey(name + "_Changed"));

    }


    public void setObservable(boolean isObservable) {
        this.isObservable = isObservable;
    }


    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

}
