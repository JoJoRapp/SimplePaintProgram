package com.example.simplepaintprogram.controller;

import com.example.simplepaintprogram.model.CircleEditable;
import com.example.simplepaintprogram.model.RectangleEditable;
import com.example.simplepaintprogram.model.ShapeFactory;
import com.example.simplepaintprogram.model.ShapeRepository;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;

import java.io.File;

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

    private SpinnerValueFactory.DoubleSpinnerValueFactory widthSpinnerValueFactory;
    private SpinnerValueFactory.DoubleSpinnerValueFactory heightSpinnerValueFactory;
    private SpinnerValueFactory.DoubleSpinnerValueFactory radiusSpinnerValueFactory;

    private GraphicsContext graphicsContext;

    private ShapeFactory shapeFactory;
    private ShapeRepository shapeRepository;
    private Shape selectedShape;

    private FileChooser fileChooser;

    private String mode = "rectangle";

    @FXML
    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        colorPicker.setOnAction(e -> {

            System.out.println("colorPicker" + colorPicker.getValue());

            if (mode.equalsIgnoreCase("select")) {
                selectedShape.setFill(colorPicker.getValue());
                graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                shapeRepository.drawShapes(graphicsContext);
            }
        });

        widthSpinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE);
        widthSpinner.setValueFactory(widthSpinnerValueFactory);
        heightSpinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE);
        heightSpinner.setValueFactory(heightSpinnerValueFactory);
        radiusSpinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE);
        radiusSpinner.setValueFactory(radiusSpinnerValueFactory);

        shapeFactory = new ShapeFactory();
        shapeRepository = new ShapeRepository();

        fileChooser = new FileChooser();
        fileChooser.setInitialFileName("image.svg");

        widthSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mode.equalsIgnoreCase("select")) {
                graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                shapeRepository.drawShapes(graphicsContext);
            }
        });
        heightSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mode.equalsIgnoreCase("select")) {
                graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                shapeRepository.drawShapes(graphicsContext);
            }
        });
        radiusSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mode.equalsIgnoreCase("select")) {
                graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                shapeRepository.drawShapes(graphicsContext);
            }
        });
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
    public void onSaveButtonClick() {
        File selectedFile = fileChooser.showSaveDialog(canvas.getScene().getWindow());
        shapeRepository.saveToSvg(selectedFile);
    }


    @FXML
    public void onCanvasClicked(MouseEvent mouseEvent) {
        if (mode.equalsIgnoreCase("select")) {
            if (selectedShape != null) {
                Class<? extends Shape> shapeClass = selectedShape.getClass();
                if (shapeClass.equals(CircleEditable.class)) {
                    CircleEditable circle = (CircleEditable) selectedShape;
                    circle.radiusProperty().unbind();
                } else if (shapeClass.equals(RectangleEditable.class)) {
                    RectangleEditable rectangle = (RectangleEditable) selectedShape;
                    rectangle.widthProperty().unbind();
                    rectangle.heightProperty().unbind();
                }
            }

            selectedShape = shapeRepository.getSelectedShape(mouseEvent);
            if (selectedShape == null) {
                return;
            }
            colorPicker.setValue((Color) selectedShape.getFill());

            Class<? extends Shape> shapeClass = selectedShape.getClass();
            if (shapeClass.equals(CircleEditable.class)) {
                CircleEditable circle = (CircleEditable) selectedShape;
                radiusSpinnerValueFactory.setValue(circle.getRadius());
                circle.radiusProperty().bind(radiusSpinner.valueProperty());
            } else if (shapeClass.equals(RectangleEditable.class)) {
                RectangleEditable rectangle = (RectangleEditable) selectedShape;
                widthSpinnerValueFactory.setValue(rectangle.getWidth());
                heightSpinnerValueFactory.setValue(rectangle.getHeight());
                rectangle.widthProperty().bind(widthSpinner.valueProperty());
                rectangle.heightProperty().bind(heightSpinner.valueProperty());
            }

            System.out.println(selectedShape.getFill());

        } else {
            Shape shape = shapeFactory.getShape(mode);
            shapeRepository.addShape(shape);

            graphicsContext.setFill(colorPicker.getValue());
            shape.setFill(graphicsContext.getFill());
            if (mode.equals("rectangle")) {
                RectangleEditable rectangle = (RectangleEditable) shape;
                rectangle.setX(mouseEvent.getX());
                rectangle.setY(mouseEvent.getY());
                rectangle.setWidth(widthSpinner.getValue());
                rectangle.setHeight(heightSpinner.getValue());
                rectangle.draw(graphicsContext);
            } else if (mode.equals("circle")) {
                CircleEditable circle = (CircleEditable) shape;
                circle.setCenterX(mouseEvent.getX());
                circle.setCenterY(mouseEvent.getY());
                circle.setRadius(radiusSpinner.getValue());
                circle.draw(graphicsContext);
            }
        }
    }
}