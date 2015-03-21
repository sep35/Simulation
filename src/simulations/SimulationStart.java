package simulations;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Application is launched in this class
 *
 * @author allankiplagat
 *
 */
public class SimulationStart extends Application {

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage stage) throws Exception {
        SimulationScene cellScene = new SimulationScene();
        cellScene.buildScene(stage);
    }
}
