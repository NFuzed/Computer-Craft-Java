package graphical.controller.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;

public class Camera extends PerspectiveCamera implements InputProcessor {

    private final CameraInputController cameraInputController;

    public Camera() {
        super(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.near = 1;
        this.far = 100;
        this.position.set(0, 0, 10);
        lookAt(0, 0, 0);
        update();

        cameraInputController = new CameraInputController(this);
        Gdx.input.setInputProcessor(cameraInputController);
    }

    public CameraInputController getCameraInputController() {
        return cameraInputController;
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int button) {
        if (button == 0) { // left mouse button
            float deltaX = Gdx.input.getDeltaX();
            float deltaY = Gdx.input.getDeltaY();

            // rotate around object when dragging on x-axis
            rotateAround(Vector3.Zero, Vector3.Y, deltaX);

            // move up and down when dragging on y-axis
            position.add(0, deltaY * 0.1f, 0);

            update();
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    public void resize(int width, int height) {
        viewportWidth = width;
        viewportHeight = height;
        update();
    }
}
