package graphical.controller.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;

public class Camera extends PerspectiveCamera implements InputProcessor {

    private static final Vector3 BASE_VIEWPOINT = new Vector3(0, 0, 10);
    private final CameraInputController cameraInputController;
    private final Vector3 focalPoint;

    public Camera() {
        super(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.focalPoint = new Vector3(0, 0, 0);
        this.near = 1;
        this.far = 100;
        this.position.set(BASE_VIEWPOINT);
        update();

        cameraInputController = new CameraInputController(this);
        Gdx.input.setInputProcessor(cameraInputController);
    }

    public void moveCameraPosition(Vector3 displacement) {
        this.position.add(displacement);
        this.focalPoint.add(displacement);
        this.lookAt(focalPoint);
    }

    public void setFocalPoint(Vector3 focalPoint) {
        this.focalPoint.set(focalPoint);

        this.position.set(focalPoint);
        this.position.sub(BASE_VIEWPOINT);

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
            rotateAround(focalPoint, Vector3.Y, deltaX);

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
