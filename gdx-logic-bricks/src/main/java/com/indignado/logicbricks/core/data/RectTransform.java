package com.indignado.logicbricks.core.data;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Rubentxu.
 */
public class RectTransform extends Transform3D{

    public static enum Axis { Horizontal, Vertical }
    public static enum Edge { Left, Right, Top, Bottom }

    public Rectangle rect = new Rectangle();
    public Vector2 anchoredPosition = new Vector2();
    public Vector2 anchorMax = new Vector2();
    public Vector2 anchorMin = new Vector2();
    public Vector2 offsetMax = new Vector2();
    public Vector2 offsetMin = new Vector2();
    public Vector2 pivot = new Vector2();
    public Vector2 sizeDelta = new Vector2();


    @Override
    public void reset() {
        rect.set(0,0,1,1);
        anchoredPosition.set(0,0);
        anchorMax.set(0,0);
        anchorMin.set(0,0);
        offsetMax.set(0,0);
        offsetMin.set(0,0);
        pivot.set(0,0);
        sizeDelta.set(0,0);

    }

}
