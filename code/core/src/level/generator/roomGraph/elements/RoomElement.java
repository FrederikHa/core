package level.generator.roomGraph.elements;

import level.tools.LevelElement;

public enum RoomElement {
    SKIP,
    WALL,
    FLOOR,
    DOOR,
    PLACED_DOOR;

    public LevelElement getLevelElement() {
        switch (this) {
            case PLACED_DOOR:
            case FLOOR:
                return LevelElement.FLOOR;
            case DOOR:
            case WALL:
                return LevelElement.WALL;
            case SKIP:
            default:
                return LevelElement.VOID;
        }
    }
}
