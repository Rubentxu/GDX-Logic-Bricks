package com.indignado.logicbricks.bricks.exceptions;

import com.badlogic.gdx.Gdx;

/**
 * @author Rubentxu.
 */
public class LogicBricksException extends RuntimeException{

    public LogicBricksException (String tag,String message) {
        super (message);
        if(Gdx.app != null) {
            Gdx.app.log(tag,message);
        } else {
            System.out.println(tag + " : " + message);
        }

    }


    public LogicBricksException (Throwable cause) {
        super (cause);

        if(Gdx.app != null) {
            Gdx.app.log("LogicBricksException",cause.getMessage());
        } else {
            System.out.println( "LogicBricksException : " + cause.getMessage());
        }

    }

}
