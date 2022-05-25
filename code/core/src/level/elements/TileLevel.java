package level.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.Painter;
import java.util.Random;
import level.elements.room.Tile;
import level.tools.Coordinate;
import level.tools.LevelElement;
import tools.Point;

public class TileLevel implements ILevel {
    private final Random random;
    private final Tile[][] tiles;
    private final Tile start;
    private final Tile end;

    /**
     * new level with random start and end
     *
     * @param tiles tiles of level
     */
    public TileLevel(final Tile[][] tiles) {
        this(tiles, new Random());
    }

    /**
     * new level with random start and end
     *
     * <p>the given random object is used for the position generation
     *
     * @param tiles tiles of level
     * @param randomGenerator used Random object for generation
     */
    public TileLevel(final Tile[][] tiles, final Random randomGenerator) {
        random = randomGenerator;
        this.tiles = tiles;
        start = getRandomFloorTile();
        end = getRandomFloorTile();
    }

    /**
     * new level with given start and end tile
     *
     * @param tiles tiles of level
     * @param start start tile, must be in <code>tiles</code>
     * @param end end tile, must be in <code>tiles</code>
     */
    TileLevel(final Tile[][] tiles, final Tile start, final Tile end) {
        this(tiles, start, end, new Random());
    }

    /**
     * new level with given start and end tile
     *
     * @param tiles tiles of level
     * @param start start tile, must be in <code>tiles</code>
     * @param end end tile, must be in <code>tiles</code>
     * @param randomGenerator used Random object for generation
     */
    TileLevel(
            final Tile[][] tiles, final Tile start, final Tile end, final Random randomGenerator) {
        random = randomGenerator;
        this.tiles = tiles;
        this.start = start;
        this.end = end;
    }

    @Override
    public Tile getRandomFloorTile() {
        Tile randomTile;
        do {
            randomTile = tiles[random.nextInt(tiles.length)][random.nextInt(tiles[0].length)];
        } while (!randomTile.isAccessible());
        return randomTile;
    }

    @Override
    public Tile getTileAt(final Coordinate globalPoint) {
        if (globalPoint.x < 0
                || globalPoint.x >= tiles.length
                || globalPoint.y < 0
                || globalPoint.y >= tiles[0].length) return null;
        return tiles[globalPoint.x][globalPoint.y];
    }

    @Override
    public Tile getStartTile() {
        return start;
    }

    @Override
    public Tile getEndTile() {
        return end;
    }

    @Override
    public void drawLevel(final Painter painter, final SpriteBatch batch) {
        for (final Tile[] tile : tiles)
            for (int x = 0; x < tiles[0].length; x++) {
                final Tile t = tile[x];
                if (t.getLevelElement() != LevelElement.SKIP)
                    painter.draw(
                            t.getTexturePath(),
                            new Point(t.getCoordinate().x, t.getCoordinate().y),
                            batch);
            }
    }
}
