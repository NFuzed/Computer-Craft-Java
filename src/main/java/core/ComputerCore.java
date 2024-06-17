package core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import graphical.controller.camera.Camera;
import graphical.geometry.CubeBuilder;
import graphical.geometry.Direction;
import graphical.geometry.Turtle;


public class ComputerCore extends ApplicationAdapter {


    private ApplicationContext applicationContext;
    private ModelBatch modelBatch;
    private ModelInstance cubeInstance;
    private Turtle turtle;

    @Override
    public void create() {
        applicationContext = new ApplicationContext();
        modelBatch = new ModelBatch();

        cubeInstance = new CubeBuilder()
                .setColor(Color.GREEN)
                .setPosition(0, 0, 0)
                .create();

        turtle = new Turtle(new Vector3(0, 0, 1), Direction.EAST);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Camera camera = applicationContext.getCamera();
        camera.getCameraInputController().update();

        modelBatch.begin(camera);
        modelBatch.render(cubeInstance);
        modelBatch.render(turtle.getModelInstance());
        modelBatch.end();
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        applicationContext.getCamera().resize(width, height);

    }
}