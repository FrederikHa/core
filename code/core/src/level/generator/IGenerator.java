package level.generator;

import level.elements.ILevel;
import level.elements.graph.Graph;
import level.generator.dungeong.graphg.NoSolutionException;
import level.tools.DesignLabel;

public interface IGenerator {
    /**
     * Get a level with a random configuration.
     *
     * @return The level.
     */
    ILevel getLevel() throws NoSolutionException;

    /**
     * Get a leve with the given configuration.
     *
     * @param designLabel The design of the level.
     * @return The level.
     */
    default ILevel getLevel(DesignLabel designLabel) throws NoSolutionException {
        return getLevel();
    }

    /**
     * Get a leve with the given configuration.
     *
     * @param nodes Number of nodes in the level-graph.
     * @param edges Number of (extra) edges in the level-graph.
     * @return The level.
     * @throws NoSolutionException If no solution can be found for the given configuration.
     */
    default ILevel getLevel(int nodes, int edges) throws NoSolutionException {
        return getLevel();
    }

    /**
     * Get a leve with the given configuration.
     *
     * @param nodes Number of nodes in the level-graph.
     * @param edges Number of (extra) edges in the level-graph.
     * @param designLabel The design of the level.
     * @return The level.
     * @throws NoSolutionException If no solution can be found for the given configuration.
     */
    default ILevel getLevel(int nodes, int edges, DesignLabel designLabel)
            throws NoSolutionException {
        return getLevel();
    }

    /**
     * Generate a Level from a given graph.
     *
     * @param graph The Level-Graph.
     * @param designLabel The Design-Label the level should have.
     * @return The level.
     * @throws NoSolutionException If no solution can be found for the given configuration.
     */
    default ILevel getLevel(Graph graph, DesignLabel designLabel) throws NoSolutionException {
        return getLevel();
    }

    /**
     * Generate a Level from a given graph.
     *
     * @param graph The Level-Graph.
     * @return The level.
     * @throws NoSolutionException If no solution can be found for the given configuration.
     */
    default ILevel getLevel(Graph graph) throws NoSolutionException {
        return getLevel();
    }
}
