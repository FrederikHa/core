package level.tools;

/**
 * Each type of field in a level can be represented by an integer value.
 *
 * @author Andre Matutat
 */
public enum LevelElement {
    VOID(-2),
    /** This field is a floor-field */
    FLOOR(0),
    /** This field is a wall-field */
    WALL(1),
    /** This field is the exit-field to the next level */
    EXIT(3);

    private int value;

    LevelElement(int value) {
        this.value = value;
    }

    /**
     * returns the value of the element
     *
     * @return the value of the element
     */
    public int getValue() {
        return value;
    }
}
