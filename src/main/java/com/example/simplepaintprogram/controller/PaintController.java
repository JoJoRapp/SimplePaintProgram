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
    public Button saveButton;

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

    private String mode = "rectangle";

    @FXML
    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        colorPicker.setOnAction(e -> {

            System.out.println("colorPicker" + colorPicker.getValue());

            if (mode.equalsIgnoreCase("select") && selectedShape != null) {
                invoker.runCommand(new ChangeShapeColor(selectedShape, colorPicker.getValue()));
                updateCanvas();
            }
        });

        invoker = new InteractionInvoker();

        shapeFactory = new ShapeFactory();
        shapeRepository = new ShapeRepository();

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
        mode = "rectangle";
    }

    @FXML
    public void onCircleButtonClick() {
        circleButton.setDisable(true);
        rectangleButton.setDisable(false);
        mode = "circle";
    }

    @FXML
    public void onSelectButtonClick() {
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

            System.out.println(selectedShape.getFill());

        } else {
            ShapeEditable shape = shapeFactory.getShape(mode, mouseEvent, spinners);
            graphicsContext.setFill(colorPicker.getValue());
            shape.setFill(graphicsContext.getFill());

            invoker.runCommand(new AddShape(shape, shapeRepository));

            shape.draw(graphicsContext);
        }
    }
}