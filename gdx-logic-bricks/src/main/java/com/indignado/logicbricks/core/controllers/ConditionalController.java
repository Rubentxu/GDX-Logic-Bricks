package com.indignado.logicbricks.core.controllers;

/**
 * @author Rubentxu.
 */
public class ConditionalController extends Controller {
    public Type type;

    public ConditionalController setType(Type type) {
        this.type = type;
        return this;

    }


    public enum Type {AND, OR, NAND, NOR}

}
