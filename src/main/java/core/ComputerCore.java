package core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import computercraft.turtle.Turtle;

public class ComputerCore extends ApplicationAdapter {
    private ApplicationContext applicationContext;
    private Stage stage;
    private Skin skin;
    private TextureAtlas atlas;
    private SelectBox<Turtle> turtleSelector;

    @Override
    public void create() {
        applicationContext = new ApplicationContext();
        applicationContext.getTurtleWebSocketServer().runOnNewThread();
        applicationContext.getModelManager().populateWithTestModels();

        // Initialize the stage and skin for HUD
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
//        skin = new Skin(Gdx.files.internal("uiskin.json"));
        atlas = new TextureAtlas(Gdx.files.internal("assets/ui/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("assets/ui/uiskin.json"), atlas);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage); // Add the stage first so it can handle UI input
        inputMultiplexer.addProcessor(applicationContext.getCamera().getCameraInputController()); // Add the camera input controller
        Gdx.input.setInputProcessor(inputMultiplexer);

        // Create HUD
        createHUD();
    }

    private void createHUD() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Add buttons to the HUD
        table.top();
        table.defaults().pad(10);

        for (int i = 0; i < 1; i++) {
            table.add(createButton("MOVE", () -> turtleSelector.getSelected().getCommandController().getTurtleCommands().moveForward())).pad(10);
            table.add(createButton("TURN LEFT", () -> turtleSelector.getSelected().getCommandController().getTurtleCommands().turnLeft())).pad(10);
            table.add(createButton("MINE", () -> turtleSelector.getSelected().getCommandController().getTurtleCommands().dig())).pad(10);
            table.add(createButton("SCAN", () -> turtleSelector.getSelected().getCommandController().getScannerCommands().scanBlocks(8))).pad(10);
        }

        this.turtleSelector = new SelectBox<>(skin);
        table.add(turtleSelector);

        applicationContext.getTurtleManager()
                .addObserver(unused -> turtleSelector.setItems(applicationContext.getTurtleManager().getTurtleMap().values().toArray(new Turtle[]{})));

        turtleSelector.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                applicationContext.getCamera().setTurtleView(turtleSelector.getSelected());
            }
        });
    }

    private TextButton createButton(String text, Runnable runnable) {
        TextButton button = new TextButton(text, skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Thread(runnable).start();
            }
        });
        return button;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        applicationContext.getCamera().getCameraInputController().update();
        applicationContext.getModelManager().updateGraphics();
        new Thread(() -> applicationContext.getTurtleManager().update()).start();

        // Render the HUD
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        applicationContext.getModelManager().dispose();
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void resize(int width, int height) {
        applicationContext.getCamera().resize(width, height);
        stage.getViewport().update(width, height, true);
    }
}