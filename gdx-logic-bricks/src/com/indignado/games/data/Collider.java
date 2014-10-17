package com.indignado.games.data;

public class Collider {
    public boolean  enabled;	// Enabled Colliders will collide with other colliders, disabled Colliders won't.
    public boolean isTrigger;	// Is the collider a trigger?
    public String tag;          // collider tag name to filters
    public PhysicsMaterial physicsMaterial;

}