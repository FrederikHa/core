package level.generator;

import static org.junit.Assert.assertNotNull;

import level.elements.Level;
import level.generator.perlinNoise.PerlinNoiseGenerator;
import org.junit.Before;
import org.junit.Test;

public class PerlinNoiseGeneratorTest {

    private PerlinNoiseGenerator generator;
    private Level level;

    @Before
    public void setup() {
        generator = new PerlinNoiseGenerator();
        level = generator.getLevel();
    }

    @Test
    public void test_getLevel() {
        assertNotNull(level);
        assertNotNull(level.getEndTile());
        assertNotNull(level.getStartTile());
        // if the path is bigger than 0 it means, there is a path form start to end, so the level
        // can be beaten.
        assert ((level.findPath(level.getStartTile(), level.getEndTile()).getCount() > 0));
    }
}
