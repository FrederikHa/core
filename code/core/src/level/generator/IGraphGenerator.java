package level.generator;

import level.elements.GraphLevel;
import level.elements.graph.Graph;
import level.generator.dungeong.graphg.NoSolutionException;
import level.tools.DesignLabel;

public interface IGraphGenerator extends IGenerator {
    /**
     * Get a level with a random configuration.
     *
     * @return The level.
     */
    GraphLevel getLevel();

    /**
     * Get a level with the given configuration.
     *
     * @param designLabel The design of the level.
     * @return The level.
     */
    default GraphLevel getLevel(DesignLabel designLabel) {
        return getLevel();
    }

    /**
     * Get a level with the given configuration.
     *
     * @param nodes Number of nodes in the level-graph.
     * @param edges Number of (extra) edges in the level-graph.
     * @return The level.
     * @throws NoSolutionException If no solution can be found for the given configuration.
     */
    default GraphLevel getLevel(int nodes, int edges) throws NoSolutionException {
        return getLevel();
    }

    /**
     * Get a level with the given configuration.
     *
     * @param nodes Number of nodes in the level-graph.
     * @param edges Number of (extra) edges in the level-graph.
     * @param designLabel The design of the level.
     * @return The level.
     * @throws NoSolutionException If no solution can be found for the given configuration.
     */
    default GraphLevel getLevel(int nodes, int edges, DesignLabel designLabel)
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
    default GraphLevel getLevel(Graph graph, DesignLabel designLabel) throws NoSolutionException {
        return getLevel();
    }

    /**
     * Generate a Level from a given graph.
     *
     * @param graph The Level-Graph.
     * @return The level.
     * @throws NoSolutionException If no solution can be found for the given configuration.
     */
    default GraphLevel getLevel(Graph graph) throws NoSolutionException {
        return getLevel();
    }
}
