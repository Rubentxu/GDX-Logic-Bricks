package com.indignado.logicbricks.components;

import com.badlogic.ashley.core.Component;
import com.indignado.logicbricks.bricks.LogicBricks;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Rubentxu.
 */
public class LogicBricksComponent extends Component {
    public Map<String,Set<LogicBricks>> logicBricks = new HashMap<String,Set<LogicBricks>>();

}
