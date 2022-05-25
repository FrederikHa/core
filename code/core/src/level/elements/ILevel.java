package level.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.Painter;
import level.elements.room.Tile;
import level.tools.Coordinate;

public interface ILevel {

    void drawLevel(Painter painter, SpriteBatch batch);

    /**
     * @return a random Floor-Tile in the Level
     */
    Tile getRandomFloorTile();

    /**
     * Get a tile on the global position.
     *
     * @param globalPoint Position form where to get the tile.
     * @return The tile on that point.
     */
    Tile getTileAt(Coordinate globalPoint);

    /**
     * Get the start tile.
     *
     * @return The start tile.
     */
    Tile getStartTile();

    /**
     * Get the end tile.
     *
     * @return The end tile.
     */
    Tile getEndTile();
}
