package core;

import javafx.application.Application;
import javafx.stage.Stage;
import menus.MenuHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : First created by Saif Amzi with code by Anh Pham, Dominic Walters, Evgeniy Kim, Hung Hoang, and Paul Popa
 * @date : 28/01/16, last edited by Dominic Walters and Paul Popa on 19/02/16
 */

public class CoreGUI extends Application {
    private static final Logger LOG = Logger.getLogger(CoreGUI.class.getName());
    private static CoreGUI instance;

    public static CoreGUI Instance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        @SuppressWarnings("unused")
        MenuHandler menuHandler = new MenuHandler(primaryStage);
        MenuHandler.switchScene(MenuHandler.MAIN_MENU);
        primaryStage.setOnCloseRequest(e -> {
            CoreEngine engine = CoreEngine.Instance();
            if (engine != null) {
                CoreEngine.Instance().setRunning(false);
            }
            LOG.log(Level.INFO, "Happy Times!");
            System.exit(0);
        });
        primaryStage.show();
    }

    public int getHEIGHT() {
        return 720;
    }

    public int getWIDTH() {
        return 1280;
    }
}
