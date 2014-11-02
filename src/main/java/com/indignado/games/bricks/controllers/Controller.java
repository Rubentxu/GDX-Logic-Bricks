package com.indignado.games.bricks.controllers;

/**
 * @author Rubentxu.
 */
public abstract class Controller {
    public String name;
    public String state;

    public abstract Boolean evaluate();

}
