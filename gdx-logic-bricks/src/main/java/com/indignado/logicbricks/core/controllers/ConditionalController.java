package com.indignado.logicbricks.core.controllers;

/**
 * @author Rubentxu.
 */
public class ConditionalController extends Controller {
    public enum Op {
        OP_NILL,
        OP_AND,
        OP_OR,
        OP_XOR,
        OP_NAND,
        OP_NOR,
        OP_XNOR,
    }

    public Op op;
    public boolean isInverter = false;


}
