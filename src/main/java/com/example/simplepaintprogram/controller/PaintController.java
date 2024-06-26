package com.example.simplepaintprogram.controller;

import com.example.simplepaintprogram.model.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PaintController {

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button circleButton;
    @FXML
    private Button rectangleButton;
    @FXML
    public Button selectButton;

    @FXML
    public Button saveButton;

    @FXML
    public Button connectButton;

    @FXML
    private Spinner<Double> widthSpinner;
    @FXML
    private Spinner<Double> heightSpinner;
    @FXML
    private Spinner<Double> radiusSpinner;
    private List<Spinner<Double>> spinners;
    private List<SpinnerValueFactory.DoubleSpinnerValueFactory> spinnerValueFactories;

    private GraphicsContext graphicsContext;

    private InteractionInvoker invoker;

    private ShapeFactory shapeFactory;
    private ShapeRepository shapeRepository;
    private ShapeEditable selectedShape;

    private FileChooser fileChooser;

    private Client client;

    private String mode = "rectangle";

    @FXML
    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        colorPicker.setOnAction(e -> {
            if (mode.equalsIgnoreCase("select") && selectedShape != null) {
                invoker.runCommand(new ChangeShapeColor(selectedShape, colorPicker.getValue()));
                updateCanvas();
            }
        });

        shapeFactory = new ShapeFactory();
        shapeRepository = new ShapeRepository();

        invoker = new InteractionInvoker();
        client = new Client(invoker, shapeFactory, shapeRepository, graphicsContext, canvas);

        initializeSpinners();

        fileChooser = new FileChooser();
        fileChooser.setInitialFileName("image.svg");
    }

    private void initializeSpinners() {
        SpinnerValueFactory.DoubleSpinnerValueFactory widthSpinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE);
        widthSpinner.setValueFactory(widthSpinnerValueFactory);
        SpinnerValueFactory.DoubleSpinnerValueFactory heightSpinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE);
        heightSpinner.setValueFactory(heightSpinnerValueFactory);
        SpinnerValueFactory.DoubleSpinnerValueFactory radiusSpinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE);
        radiusSpinner.setValueFactory(radiusSpinnerValueFactory);

        spinnerValueFactories = new ArrayList<>(3);
        spinnerValueFactories.add(widthSpinnerValueFactory);
        spinnerValueFactories.add(heightSpinnerValueFactory);
        spinnerValueFactories.add(radiusSpinnerValueFactory);

        spinners = new ArrayList<>(3);
        spinners.add(widthSpinner);
        spinners.add(heightSpinner);
        spinners.add(radiusSpinner);

        widthSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedShape != null) {
                if (mode.equalsIgnoreCase("select") && selectedShape.getClass().equals(RectangleEditable.class)) {
                    invoker.runCommand(new ChangeRectangleWidth((RectangleEditable) selectedShape, newValue));
                    updateCanvas();
                }
            }
        });
        heightSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedShape != null) {
                if (mode.equalsIgnoreCase("select") && selectedShape.getClass().equals(RectangleEditable.class)) {
                    invoker.runCommand(new ChangeRectangleHeight((RectangleEditable) selectedShape, newValue));
                    updateCanvas();
                }
            }
        });
        radiusSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedShape != null) {
                if (mode.equalsIgnoreCase("select") && selectedShape.getClass().equals(CircleEditable.class)) {
                    invoker.runCommand(new ChangeCircleRadius((CircleEditable) selectedShape, newValue));
                    updateCanvas();
                }
            }
        });
    }

    private void updateCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        shapeRepository.drawShapes(graphicsContext);
    }

    @FXML
    public void onRectangleButtonClick() {
        rectangleButton.setDisable(true);
        circleButton.setDisable(false);
        selectButton.setDisable(false);
        mode = "rectangle";
    }

    @FXML
    public void onCircleButtonClick() {
        circleButton.setDisable(true);
        rectangleButton.setDisable(false);
        selectButton.setDisable(false);
        mode = "circle";
    }

    @FXML
    public void onSelectButtonClick() {
        selectButton.setDisable(true);
        circleButton.setDisable(false);
        rectangleButton.setDisable(false);
        mode = "select";
    }

    @FXML
    public void onUndoButtonClick() {
        invoker.undo();
        updateCanvas();
    }

    @FXML
    public void onRedoButtonClick() {
        invoker.redo();
        updateCanvas();
    }

    @FXML
    public void onSaveButtonClick() {
        File selectedFile = fileChooser.showSaveDialog(canvas.getScene().getWindow());
        shapeRepository.saveToSvg(selectedFile);
    }


    @FXML
    public void onCanvasClicked(MouseEvent mouseEvent) {
        if (mode.equalsIgnoreCase("select")) {
            selectedShape = shapeRepository.getSelectedShape(mouseEvent);
            if (selectedShape == null) {
                return;
            }
            colorPicker.setValue((Color) selectedShape.getFill());
            selectedShape.setSpinners(spinnerValueFactories);

        } else {
            ShapeEditable shape = shapeFactory.getShape(
                    mode,
                    mouseEvent.getX(),
                    mouseEvent.getY(),
                    spinners.get(0).getValue(),
                    spinners.get(1).getValue(),
                    spinners.get(2).getValue()
            );
            graphicsContext.setFill(colorPicker.getValue());
            shape.setFill(graphicsContext.getFill());

            invoker.runCommand(new AddShape(shape, shapeRepository));

            shape.draw(graphicsContext);
        }
    }

    @FXML
    public void onConnectButtonClick() {
        Thread clientThread = new Thread(client);
        clientThread.start();
        while (!client.isConnected()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        connectButton.setDisable(true);
        invoker = new InteractionSender(client);
    }
}