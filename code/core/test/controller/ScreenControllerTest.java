package controller;

import static org.mockito.Mockito.doNothing;
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
import resourceLoading.ResourceController;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MainController.class, Gdx.class})
class ScreenControllerTest {
    ScreenController controller;
    SpriteBatch batch;
    SpriteBatch hudBatch;
    DungeonCamera camera;
    ResourceController resourceController;
    int someArbitraryValueGreater0forDelta = 7;

    // Because of use of PowerMockRunner we need an empty constructor here
    public ScreenControllerTest() {}

    @Before
    public void setUp() throws Exception {
        batch = Mockito.mock(SpriteBatch.class);
        hudBatch = Mockito.mock(SpriteBatch.class);
        camera = Mockito.mock(DungeonCamera.class);
        resourceController = Mockito.mock(ResourceController.class);

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
        HUDController hudController = Mockito.mock(HUDController.class);
        doNothing().when(hudController).update();
        PowerMockito.whenNew(HUDController.class).withAnyArguments().thenReturn(hudController);
        PowerMockito.whenNew(LevelAPI.class)
                .withAnyArguments()
                .thenReturn(Mockito.mock(LevelAPI.class));
        PowerMockito.whenNew(DungeonCamera.class)
                .withAnyArguments()
                .thenReturn(Mockito.mock(DungeonCamera.class));
        PowerMockito.whenNew(RandomWalkGenerator.class)
                .withAnyArguments()
                .thenReturn(Mockito.mock(RandomWalkGenerator.class));

        controller = Mockito.spy(ScreenController.class);
        controller.initilize(batch, hudBatch, camera, resourceController);
    }

    @Test
    public void test_show() {
        Mockito.verify(controller).initilize(batch, hudBatch, camera, resourceController);
        Mockito.verify(controller).setup();

        controller.show();
        Mockito.verify(controller).show();
        Mockito.verify(controller).gainFocus();
        Mockito.verifyNoMoreInteractions(controller);
    }

    @Test
    public void test_render() {
        Mockito.verify(controller).initilize(batch, hudBatch, camera, resourceController);
        Mockito.verify(controller).setup();

        controller.show();
        Mockito.verify(controller).show();
        Mockito.verify(controller).gainFocus();
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
        when(controller.stopLoop()).thenReturn(true);
        Mockito.verify(controller).initilize(batch, hudBatch, camera, resourceController);
        Mockito.verify(controller).setup();

        controller.show();
        Mockito.verify(controller).show();
        Mockito.verify(controller).gainFocus();
        Mockito.verifyNoMoreInteractions(controller);

        controller.render(someArbitraryValueGreater0forDelta);
        Mockito.verify(controller).render(someArbitraryValueGreater0forDelta);
        Mockito.verify(controller, Mockito.never()).beginFrame();
        Mockito.verify(controller, Mockito.never()).endFrame();
        Mockito.verify(controller, Mockito.times(1)).stopLoop();
        Mockito.verifyNoMoreInteractions(controller);
    }
}
