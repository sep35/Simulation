package simulations;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Class is used to test new implementations of the Grid class.
 * Not used in actual application.
 *
 * @author allankiplagat
 *
 */
public class GridTester extends Application {
    public static final double SCENE_WIDTH = 600;
    public static final double SCENE_HEIGHT = 600;
    public static final double FRAME_DURATION = 1000;
    private static int GRID_DEFAULT_SIDE = 10;
    private Duration duration;
    private EventHandler<ActionEvent> frame = new EventHandler<ActionEvent>() {
        @Override
        public void handle (ActionEvent arg0) {
            System.out.println("Frame passed\n");
            doFrameStuff();
        }
    };
    private Grid grid;

    @Override
    public void start (Stage stage) throws Exception {
        duration = new Duration(FRAME_DURATION);
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cell Automata");
        stage.show();

        KeyFrame keyFrame = new KeyFrame(duration, frame);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        grid = new HexagonalGrid(GRID_DEFAULT_SIDE, GRID_DEFAULT_SIDE);
        borderPane.setCenter(grid);
    }

    public static final void main (String[] args) {
        launch(args);
    }

    public void doFrameStuff () {
        // insert testing code here
    }

}
