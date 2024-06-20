package core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;


public class ComputerCore extends ApplicationAdapter {


    private ApplicationContext applicationContext;


    @Override
    public void create() {
        applicationContext = new ApplicationContext();
        applicationContext.getTurtleWebSocketServer().runOnNewThread();
        applicationContext.getModelManager().populateWithTestModels();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        applicationContext.getCamera().getCameraInputController().update();
        applicationContext.getModelManager().renderAllModels();
        applicationContext.getTurtleManager().update();
    }

    @Override
    public void dispose() {
        applicationContext.getModelManager().dispose();
    }

    @Override
    public void resize(int width, int height) {
        applicationContext.getCamera().resize(width, height);

    }
}