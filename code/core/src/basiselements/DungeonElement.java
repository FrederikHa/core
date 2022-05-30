package basiselements;

import tools.Point;

public abstract class DungeonElement {

    /** An object in the dungeon that can be drawn */
    public DungeonElement() {}

    /** Will be executed every frame. */
    public void update() {}

    /** Draws this instance on the batch. */
    public void draw() {}

    /**
     * @return <code>true</code>, if this instance can be deleted; <code>false</code> otherwise
     */
    public boolean removable() {
        return false;
    }

    /**
     * @return the exact position in the dungeon of this instance
     */
    public abstract Point getPosition();

    /**
     * @return the (current) Texture-Path of the object
     */
    public abstract String getTexturePath();
}
