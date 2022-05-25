package level.generator;

import level.elements.ILevel;

public interface IGenerator {
    /**
     * Get a level with a random configuration.
     *
     * @return The level.
     */
    ILevel getLevel();

    /**
     * Get a level with the given seed.
     *
     * @param seed The seed of the level.
     * @return The level.
     */
    default ILevel getLevel(long seed) {
        return getLevel();
    }
}
