package core;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sceneElements.ElementsHandler;

import java.util.logging.Logger;

/**
 * Created by Dominic on 09/02/2016.
 */
public class GameRunTime {
    private static final Logger LOG = Logger.getLogger(GameRunTime.class.getName());

    private Renderer renderer;
    private CoreEngine engine;

    Pane mainGamePane = null;
    static Scene mainGameScene = null;
    Group mainGame = null;

    private static GameRunTime instance;

    public static GameRunTime Instance() {
        return instance;
    }

    /**
     * Constructor for the game run time.
     * Initialises the engine, renderer, and unit spawner
     */
    public GameRunTime() {
        this.instance = this;
        Thread engThread = new Thread(() ->
        {
            this.engine = new CoreEngine();
            this.engine.startGame();
        });
        engThread.start();
        while (this.engine == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        declareElements();
        this.renderer = new Renderer(mainGameScene);
        rendererSpecificInit();
        //renderer.initialDraw();
    }

    public void declareElements() {
        mainGamePane = new BorderPane();
        mainGame = new Group(mainGamePane);
        mainGameScene = new Scene(mainGame, CoreGUI.WIDTH, CoreGUI.HEIGHT);
        mainGameScene.setOnKeyPressed(e -> ElementsHandler.handleKeys(e));
    }

    /**
     * Get the renderer to draw on
     *
     * @return the renderer
     */
    public Renderer getRenderer() {
        return this.renderer;
    }

    /**
     * Get the engine the game is playing on
     *
     * @return the engine
     */
    public CoreEngine getEngine() {
        return this.engine;
    }

    /**
     * Renderer specific initialisation that isn't necessary and could be partially removed.
     */
    private void rendererSpecificInit() {
        mainGameScene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) ->
        {
            this.renderer.redraw();
        });
        mainGameScene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) ->
        {
            this.renderer.redraw();
        });
        ((BorderPane) ((Group) mainGameScene.getRoot()).getChildren().get(0)).setCenter(this.renderer);
    }

    /**
     * Returns the main game scene where the game will be played
     *
     * @return - the main game scene
     */
    public static Scene getScene() {
        return mainGameScene;
    }

    public void startGame() {
        UnitSpawner spawner = new UnitSpawner(this);
        //dirty setting
        spawner.setSpawnlimit(1);
        engine.setSpawner(spawner);
    }
}