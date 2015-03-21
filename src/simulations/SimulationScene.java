package simulations;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Class drives the simulation GUI
 *
 * @author allankiplagat, stevenpierre, minkwonlee
 *
 */
public class SimulationScene {
    private SimulationManager manager;
    private Grid grid;
    private GridColoringStrategy gridColoringStrategy;
    private Timeline animation;
    private Stage stage;
    // Locale objects available for customizing program for different regions
    private Locale[] supportedLocales = {
                                         new Locale("en", "US"),
                                         new Locale("fr", "FR")
    };
    private Text statusText;
    private XMLFileReader fileReader;
    private int xCellsCount;
    private int yCellsCount;
    private BorderPane borderPane;

    public static final double SCENE_WIDTH = 600;
    public static final double SCENE_HEIGHT = 600;
    public static final double GRID_TO_SCENE_RATIO = 0.8;
    public static final String NULL_STRING = "Null";
    public static final double FRAME_RATE_INCREASE_RATIO = 0.5;
    public static final double STATUS_BAR_HEIGHT = 30;
    
    public void buildScene (Stage st) {
        stage = st;
        // create borderPane to host elements in scene
        borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cell Automata");
        stage.show();

        manager = new SimulationManager(this);
        KeyFrame frame = manager.initializeGrid();
        animation = new Timeline();
        animation.setCycleCount(Animation.INDEFINITE);
        animation.getKeyFrames().add(frame);

        // load GUI text language resource bundle for this locale
        Locale currentLocale = supportedLocales[0];
        ResourceBundle guiText =
                ResourceBundle.getBundle("simulations.LanguagesBundle", currentLocale);

        // add tool bars and status bars on Top, Left and Bottom
        addToolBarsAndStatusBar(borderPane, guiText);

        grid = new HexagonalGrid(xCellsCount, yCellsCount);
        gridColoringStrategy = new GridColoringStrategy();

    }

    private void loadFileAndCreateGrid () {
        fileReader = new XMLFileReader();
        fileReader.readFile(stage);
        // create viewing grid at borderPane center
        int[] dimensions = fileReader.dimension;
        xCellsCount = dimensions[0];
        yCellsCount = dimensions[1];
        // gridPane = buildGridPane(borderPane,xCellsCount,yCellsCount);

        grid = new RectangularGrid(xCellsCount, yCellsCount);
        grid.getGridElements();

        borderPane.setCenter(grid);
        // fillGridPane(gridPane,xCellsCount,yCellsCount);
        // pass grid dimensions to cellManager
        manager.setGridDimensions(dimensions);
    }

    protected void updateGridPane (List<Unit> cellList, List<Unit> patchList) {
        // set all grid elements to white
        grid.setGridElementsWhite();

        // set relevant GridUnit properties
        for (Unit unit : cellList) {
            gridColoringStrategy.setGridElementColor(unit, grid);
        }
    }

    /**
     * Method adds tool bars and status bar, and sets button on action commands
     *
     * @param borderPane
     * @param guiText
     */
    private void addToolBarsAndStatusBar (BorderPane borderPane, ResourceBundle guiText) {
        Button loadSimulation = new Button(guiText.getString("Load simulation"));
        loadSimulation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                loadSimulation();
            }
        });
        ToolBar topToolBar = new ToolBar(
                                         loadSimulation
                );
        topToolBar.setOrientation(Orientation.HORIZONTAL);

        Button start = new Button(guiText.getString("Start"));
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                start();
            }
        });
        Button pause = new Button(guiText.getString("Pause"));
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                pause();
            }
        });
        Button stepForward = new Button(guiText.getString("Step forward"));
        stepForward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                stepForward();
            }
        });
        Button speedUp = new Button(guiText.getString("Speed up"));
        speedUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                speedUp();
            }
        });
        Button slowDown = new Button(guiText.getString("Slow down"));
        slowDown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                slowDown();
            }
        });
        ToolBar leftToolBar = new ToolBar(start, pause, stepForward, speedUp, slowDown);
        leftToolBar.setOrientation(Orientation.VERTICAL);

        HBox statusBar = new HBox();
        statusBar.setPrefHeight(STATUS_BAR_HEIGHT);
        statusBar.setAlignment(Pos.BOTTOM_LEFT);
        statusText = new Text();
        statusBar.getChildren().addAll(
                                       new Label(guiText.getString("Status") + ": "),
                                       statusText
                );

        borderPane.setTop(topToolBar);
        borderPane.setLeft(leftToolBar);
        borderPane.setBottom(statusBar);
    }

    /*
     * Methods called when buttons are pressed
     */
    private void loadSimulation () {
        // reset animation
        animation.stop();
        manager.clearCellList();
        manager.clearPatchList();

        System.out.println("LoadingSimulation\n");
        loadFileAndCreateGrid();
        // get the name of the simulation & type of cells used
        statusText.setText("Now running " + fileReader.simulation + " model");

        // if cells are present in this simulation, create them
        if (!fileReader.cellType.equals(NULL_STRING)) {
            manager.createCells(fileReader.cellType, fileReader.cellinfo);
        }
        // if patches are present in this simulation, create them
        if (!fileReader.patchType.equals(NULL_STRING)) {
            manager.createPatches(fileReader.patchType, fileReader.patchinfo);
        }
    }

    private void start () {
        // start simulation
        animation.play();
        System.out.println("Start\n");
    }

    private void pause () {
        // pause simulation
        animation.pause();
        System.out.println("Pause\n");
    }

    private void stepForward () {
        System.out.println("StepForward\n");
        // To fix
    }

    private void speedUp () {
        System.out.println("SpeedUp\n");
        animation.setRate(animation.getCurrentRate() * (1 + FRAME_RATE_INCREASE_RATIO));
    }

    private void slowDown () {
        System.out.println("SlowDown\n");
        animation.setRate(animation.getCurrentRate() * (1 - FRAME_RATE_INCREASE_RATIO));
    }

}
