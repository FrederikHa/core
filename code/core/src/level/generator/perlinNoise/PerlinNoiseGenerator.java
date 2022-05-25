package level.generator.perlinNoise;

import java.util.Random;
import java.util.logging.Logger;
import level.elements.ILevel;
import level.elements.TileLevel;
import level.elements.room.Tile;
import level.generator.IGenerator;
import level.tools.Coordinate;
import level.tools.DesignLabel;
import level.tools.LevelElement;
import level.tools.TileTextureFactory;

public class PerlinNoiseGenerator implements IGenerator {
    public static final Logger LOG = Logger.getLogger(PerlinNoiseGenerator.class.getName());

    @Override
    public TileLevel getLevel() {
        return getLevel((int) (Math.random() * 20 + 40), (int) (Math.random() * 20 + 40));
    }

    @Override
    public ILevel getLevel(long seed) {
        return getLevel((int) (Math.random() * 20 + 40), (int) (Math.random() * 20 + 40), seed);
    }

    /**
     * generates a new level
     *
     * @param width width of the level
     * @param height height of the level
     * @return The level.
     */
    public TileLevel getLevel(final int width, final int height) {
        final int seed = (int) (Math.random() * Integer.MAX_VALUE);
        LOG.info("Seed: " + seed);
        return getLevel(width, height, seed);
    }

    /**
     * generates new Level
     *
     * @param width width of level
     * @param height height of level
     * @param seed seed of level
     * @return the generated Level
     */
    public TileLevel getLevel(final int width, final int height, final long seed) {
        LOG.info("Level dimensions: " + width + " x " + height);
        final Random random = new Random(seed);
        // playing field
        final Area playingArea = generateNoiseArea(width, height, random);
        LevelElement[][] elements = toLevelElementArray(playingArea);
        DesignLabel design =
                DesignLabel.values()[new Random().nextInt(DesignLabel.values().length - 1)];
        TileLevel generatedLevel = new TileLevel(toTilesArray(elements, design));

        // end tile
        final Tile end = generatedLevel.getEndTile();
        elements[end.getCoordinate().x][end.getCoordinate().y] = LevelElement.EXIT;
        String endTexturePath =
                TileTextureFactory.findTexturePath(
                        elements[end.getCoordinate().x][end.getCoordinate().y],
                        design,
                        elements,
                        end.getCoordinate());
        System.out.println(endTexturePath);
        end.setLevelElement(LevelElement.EXIT, endTexturePath);
        return generatedLevel;
    }

    private static Area generateNoiseArea(int width, int height, Random randomGenerator) {
        final PerlinNoise pNoise =
                new PerlinNoise(width, height, new int[] {2, 3}, false, randomGenerator);
        final double[][] noise = pNoise.noiseAll(1);

        final Area[] areas = NoiseArea.getAreas(0.4, 0.6, noise, false);
        Area area = areas[0];
        for (final Area f : areas) {
            if (area.getSize() < f.getSize()) {
                area = f;
            }
        }
        return area;
    }

    private static LevelElement[][] toLevelElementArray(Area playingArea) {
        LevelElement[][] res = new LevelElement[playingArea.getWidth()][playingArea.getHeight()];
        for (int i = 0; i < playingArea.getWidth(); i++) {
            for (int j = 0; j < playingArea.getHeight(); j++) {
                if (playingArea.contains(i, j)) {
                    res[i][j] = LevelElement.FLOOR;
                } else {
                    res[i][j] = LevelElement.SKIP;
                }
            }
        }
        return res;
    }

    private static Tile[][] toTilesArray(
            final LevelElement[][] levelElements, final DesignLabel design) {
        final Tile[][] res = new Tile[levelElements.length][levelElements[0].length];
        for (int x = 0; x < res.length; x++) {
            for (int y = 0; y < res[0].length; y++) {
                String texturePath =
                        TileTextureFactory.findTexturePath(
                                levelElements[x][y], design, levelElements, new Coordinate(x, y));
                res[x][y] = new Tile(texturePath, new Coordinate(x, y), levelElements[x][y]);
            }
        }
        return res;
    }
}
