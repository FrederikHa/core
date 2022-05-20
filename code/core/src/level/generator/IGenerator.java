package level.generator;

import level.elements.Level;
import level.tools.DesignLabel;

public interface IGenerator {
    /**
     * Get a level with a random configuration.
     *
     * @return The level.
     */
    Level getLevel();

    /**
     * Get a level with a random configuration in the given design.
     *
     * @return The level.
     */
    Level getLevel(DesignLabel designLabel);
}
