package level;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.Painter;
import level.elements.Level;
import level.generator.IGenerator;
import level.tools.DesignLabel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class LevelAPITest {

    private LevelAPI api;
    private IGenerator generator;
    private Painter painter;
    private SpriteBatch batch;
    private IOnLevelLoader onLevelLoader;
    private Level level;

    @Before
    public void setup() {
        batch = Mockito.mock(SpriteBatch.class);
        painter = Mockito.mock(Painter.class);
        generator = Mockito.mock(IGenerator.class);
        onLevelLoader = Mockito.mock(IOnLevelLoader.class);
        level = Mockito.mock(Level.class);
        api = new LevelAPI(batch, painter, generator, onLevelLoader);
    }

    @Test
    public void test_loadLevel() {
        when(generator.getLevel()).thenReturn(level);
        api.loadLevel();
        verify(generator).getLevel();
        Mockito.verifyNoMoreInteractions(generator);
        verify(onLevelLoader).onLevelLoad();
        Mockito.verifyNoMoreInteractions(onLevelLoader);
        assertEquals(level, api.getCurrentLevel());
    }

    @Test
    public void test_loadLevel_withDesign() {
        when(generator.getLevel(DesignLabel.DEFAULT)).thenReturn(level);
        api.loadLevel(DesignLabel.DEFAULT);
        verify(generator).getLevel(DesignLabel.DEFAULT);
        Mockito.verifyNoMoreInteractions(generator);
        verify(onLevelLoader).onLevelLoad();
        Mockito.verifyNoMoreInteractions(onLevelLoader);
        assertEquals(level, api.getCurrentLevel());
    }

    @Test
    public void test_setLevel() {
        api.setLevel(level);
        Mockito.verifyNoInteractions(generator);
        verify(onLevelLoader).onLevelLoad();
        Mockito.verifyNoMoreInteractions(onLevelLoader);
        assertEquals(level, api.getCurrentLevel());
    }

    @Test
    public void test_update() {
        api.setLevel(level);
        api.update();

        verify(level).drawLevel(painter, batch);
        verifyNoMoreInteractions(level);
    }
}
