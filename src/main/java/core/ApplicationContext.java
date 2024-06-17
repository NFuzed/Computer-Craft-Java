package core;

import graphical.controller.camera.Camera;

public class ApplicationContext {

    private final Camera camera;

    public ApplicationContext() {
        camera = new Camera();
    }

    public Camera getCamera() {
        return camera;
    }
}
