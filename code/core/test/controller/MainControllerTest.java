package controller;

import static org.mockito.Mockito.when;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.DungeonCamera;
import graphic.Painter;
import level.LevelAPI;
import level.generator.randomwalk.RandomWalkGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MainController.class, Gdx.class})
class MainControllerTest {
    MainController controller;
    SpriteBatch batch;
    int someArbitraryValueGreater0forDelta = 7;

    // Because of use of PowerMockRunner we need an empty constructor here
    public MainControllerTest() {}

    @Before
    public void setUp() throws Exception {
        controller = Mockito.spy(MainController.class);
        batch = Mockito.mock(SpriteBatch.class);

        Whitebox.setInternalState(Gdx.class, "gl", Mockito.mock(GL20.class));

        PowerMockito.whenNew(EntityController.class)
                .withNoArguments()
                .thenReturn(Mockito.mock(EntityController.class));
        PowerMockito.whenNew(Painter.class)
                .withAnyArguments()
                .thenReturn(Mockito.mock(Painter.class));
        PowerMockito.whenNew(SpriteBatch.class)
                .withAnyArguments()
                .thenReturn(Mockito.mock(SpriteBatch.class));
        PowerMockito.whenNew(HUDController.class)
                .withAnyArguments()
                .thenReturn(Mockito.mock(HUDController.class));
        PowerMockito.whenNew(LevelAPI.class)
                .withAnyArguments()
                .thenReturn(Mockito.mock(LevelAPI.class));
        PowerMockito.whenNew(DungeonCamera.class)
                .withAnyArguments()
                .thenReturn(Mockito.mock(DungeonCamera.class));
        PowerMockito.whenNew(RandomWalkGenerator.class)
                .withAnyArguments()
                .thenReturn(Mockito.mock(RandomWalkGenerator.class));
    }

    @Test
    public void test_show() {
        controller.setSpriteBatch(batch);

        Mockito.verify(controller).setSpriteBatch(batch);
        Mockito.verifyNoMoreInteractions(controller, batch);

        controller.show();
        Mockito.verify(controller).show();
        Mockito.verify(controller).firstFocus();
        Mockito.verify(controller).gainFocus();
        Mockito.verify(controller).setup();
        Mockito.verifyNoMoreInteractions(controller);
    }

    @Test
    public void test_render() {
        controller.setSpriteBatch(batch);

        Mockito.verify(controller).setSpriteBatch(batch);
        Mockito.verifyNoMoreInteractions(controller, batch);

        controller.show();
        Mockito.verify(controller).show();
        Mockito.verify(controller).firstFocus();
        Mockito.verify(controller).gainFocus();
        Mockito.verify(controller).setup();
        Mockito.verifyNoMoreInteractions(controller);

        controller.render(someArbitraryValueGreater0forDelta);
        Mockito.verify(controller).render(someArbitraryValueGreater0forDelta);
        Mockito.verify(controller).beginFrame();
        Mockito.verify(controller).endFrame();
        Mockito.verify(controller, Mockito.times(6)).stopLoop();
        Mockito.verifyNoMoreInteractions(controller);
    }

    @Test
    public void test_render_paused() {
        controller.setSpriteBatch(batch);
        when(controller.stopLoop()).thenReturn(true);
        Mockito.verify(controller).setSpriteBatch(batch);
        Mockito.verifyNoMoreInteractions(controller, batch);

        controller.render(someArbitraryValueGreater0forDelta);
        Mockito.verify(controller).show();
        Mockito.verify(controller).setup();
        Mockito.verify(controller).render(someArbitraryValueGreater0forDelta);
        Mockito.verify(controller, Mockito.never()).beginFrame();
        Mockito.verify(controller, Mockito.never()).endFrame();
        Mockito.verify(controller, Mockito.times(1)).stopLoop();
        Mockito.verifyNoMoreInteractions(controller);
    }
}
