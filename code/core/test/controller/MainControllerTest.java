package controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.DungeonCamera;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import resourceLoading.ResourceController;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MainController.class, Lwjgl3ApplicationConfiguration.class})
class MainControllerTest {
    MainController mainController;
    ScreenController screenController;
    SpriteBatch batch;
    ResourceController resources;
    DungeonCamera camera;
    DisplayMode displayMode;

    // Because of use of PowerMockRunner we need an empty constructor here
    public MainControllerTest() {}

    private int displayWidth = 640;
    private int displayHeight = 480;

    private class MockedDisplayMode extends DisplayMode {
        MockedDisplayMode() {
            super(displayWidth, displayHeight, 30, 32);
        }
    }

    @Before
    public void setUp() throws Exception {
        screenController = Mockito.mock(ScreenController.class);
        batch = Mockito.mock(SpriteBatch.class);
        PowerMockito.whenNew(SpriteBatch.class).withNoArguments().thenReturn(batch);
        resources = Mockito.mock(ResourceController.class);
        PowerMockito.whenNew(ResourceController.class).withNoArguments().thenReturn(resources);
        camera = Mockito.mock(DungeonCamera.class);
        PowerMockito.whenNew(DungeonCamera.class).withAnyArguments().thenReturn(camera);

        mainController = Mockito.spy(MainController.class);
        PowerMockito.doNothing().when(mainController, "setScreen", screenController);
        PowerMockito.doNothing().when(batch).dispose();
        PowerMockito.mockStatic(Lwjgl3ApplicationConfiguration.class);
        displayMode = new MockedDisplayMode();
        when(Lwjgl3ApplicationConfiguration.getDisplayMode()).thenReturn(displayMode);

        mainController.create();
    }

    @Test
    public void test_create() {
        assertEquals(displayHeight, displayMode.height);
        Mockito.verify(mainController).create();
        Mockito.verify(mainController).setupCamera(displayWidth / 16f, displayHeight / 16f, 0.25f);
        Mockito.verify(mainController).setup();
        Mockito.verifyNoMoreInteractions(mainController, batch, screenController);
    }

    @Test
    public void test_dispose() {
        Mockito.verify(mainController).create();
        Mockito.verify(mainController).setupCamera(displayWidth / 16f, displayHeight / 16f, 0.25f);
        Mockito.verify(mainController).setup();
        Mockito.verifyNoMoreInteractions(mainController, batch, screenController);

        mainController.dispose();
        Mockito.verify(mainController).dispose();
        Mockito.verify(batch, Mockito.times(2)).dispose();
        Mockito.verifyNoMoreInteractions(mainController, batch, screenController);
    }
}
