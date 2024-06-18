package core;

import computercraft.TurtleManager;
import computercraft.server.TurtleWebSocketServer;
import graphical.controller.camera.Camera;
import graphical.geometry.ModelManager;

public class ApplicationContext {

    private final Camera camera;
    private final TurtleManager turtleManager;
    private final ModelManager modelManager;
    private final TurtleWebSocketServer turtleWebSocketServer;


    public ApplicationContext() {
        camera = new Camera();
        modelManager = new ModelManager(camera);
        turtleManager = new TurtleManager(modelManager);
        turtleWebSocketServer = new TurtleWebSocketServer(turtleManager);
    }

    public ModelManager getModelManager() {
        return modelManager;
    }

    public TurtleManager getTurtleManager() {
        return turtleManager;
    }

    public Camera getCamera() {
        return camera;
    }

    public TurtleWebSocketServer getTurtleWebSocketServer() {
        return turtleWebSocketServer;
    }
}
