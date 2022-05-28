package level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.Painter;
import level.elements.Level;
import level.generator.IGenerator;
import level.tools.DesignLabel;

/** Manages the level. */
public class LevelAPI {
    private final SpriteBatch batch;
    private final Painter painter;
    private final IOnLevelLoader onLevelLoader;
    private IGenerator gen;
    private Level currentLevel;

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

    /** Load a new level. */
    public void loadLevel() {
        currentLevel = gen.getLevel();
        onLevelLoader.onLevelLoad();
    }

    /**
     * Load a new level
     *
     * @param designLabel The design that the level should have
     */
    public void loadLevel(DesignLabel designLabel) {
        currentLevel = gen.getLevel(designLabel);
        onLevelLoader.onLevelLoad();
    }

    /** Draw level */
    public void update() {
        if (currentLevel == null) {
            return;
        }
        currentLevel.drawLevel(painter, batch);
    }

    public Level getCurrentLevel() {
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
     * returns the Generator of the Level
     *
     * @return the Generator of the Level
     */
    public IGenerator getGenerator() {
        return gen;
    }

    /**
     * Sets the current level to the given level and calls onLevelLoad().
     *
     * @param level The level to be set.
     */
    public void setLevel(Level level) {
        currentLevel = level;
        onLevelLoader.onLevelLoad();
    }
}
