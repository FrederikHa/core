package level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.Painter;
import level.elements.ILevel;
import level.generator.IGenerator;
import level.generator.dungeong.graphg.NoSolutionException;
import level.tools.DesignLabel;

/** Manages the level. */
public class LevelAPI {
    private final SpriteBatch batch;
    private final Painter painter;
    private final IOnLevelLoader onLevelLoader;
    private IGenerator gen;
    private ILevel currentLevel;

    /**
     * @param batch Batch on which to draw.
     * @param painter Who draws?
     * @param generator Level generator
     * @param onLevelLoader Object that implements the onLevelLoad method.
     */
    public LevelAPI(
            SpriteBatch batch,
            Painter painter,
            IGenerator generator,
            IOnLevelLoader onLevelLoader) {
        this.gen = generator;
        this.batch = batch;
        this.painter = painter;
        this.onLevelLoader = onLevelLoader;
    }

    /**
     * Load a new level.
     *
     * @throws NoSolutionException if no level can be loaded.
     */
    public void loadLevel() throws NoSolutionException {
        currentLevel = gen.getLevel();
        onLevelLoader.onLevelLoad();
    }

    /**
     * Load a new level with the given configuration.
     *
     * @param nodes Number of rooms in the level
     * @param edges Number of loops in the level
     * @param designLabel design of the level
     * @throws NoSolutionException if no level can be loaded.
     */
    public void loadLevel(int nodes, int edges, DesignLabel designLabel)
            throws NoSolutionException {
        currentLevel = gen.getLevel(nodes, edges, designLabel);
        onLevelLoader.onLevelLoad();
    }

    /** Draw level */
    public void update() {
        currentLevel.drawLevel(painter, batch);
    }

    public ILevel getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Set the level generator
     *
     * @param generator new level generator
     */
    public void setGenerator(IGenerator generator) {
        gen = generator;
    }

    /**
     * Sets the current level to the given level and calls onLevelLoad().
     *
     * @param level The level to be set.
     */
    public void setLevel(ILevel level) {
        currentLevel = level;
        onLevelLoader.onLevelLoad();
    }
}
