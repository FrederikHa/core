package level.generator.roomGraph.elements;

import level.tools.Coordinate;
import level.tools.DesignLabel;

/**
 * A Room is a collection of tiles and has a position in the global system.
 *
 * @author Andre Matutat
 */
public class Room {
    private final DesignLabel design;
    private final Coordinate referencePointGlobal;
    private final Coordinate referencePointLocal;
    private final RoomElement[][] layout;

    /**
     * @param layout List of tiles that defines the layout of the room.
     * @param label The design of the room.
     * @param referencePointLocal A point in the room to place it in the level.
     * @param referencePointGlobal The position of the local reference point in the global system.
     */
    public Room(
            RoomElement[][] layout,
            DesignLabel label,
            Coordinate referencePointLocal,
            Coordinate referencePointGlobal) {
        this.design = label;

        this.layout = layout;
        this.referencePointGlobal = referencePointGlobal;
        this.referencePointLocal = referencePointLocal;
    }

    /**
     * @return A copy of the layout.
     */
    public RoomElement[][] getLayout() {
        return layout;
    }

    public DesignLabel getDesign() {
        return design;
    }

    public Coordinate getReferencePointLocal() {
        return referencePointLocal;
    }

    public Coordinate getReferencePointGlobal() {
        return referencePointGlobal;
    }
}
