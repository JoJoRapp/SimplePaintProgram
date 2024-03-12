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

public class PaintController {

    @FXML
    private Spinner<Double> widthSpinner;
    @FXML
    private Spinner<Double> heightSpinner;
    @FXML
    private Spinner<Double> radiusSpinner;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button circleButton;
    @FXML
    private Button rectangleButton;

    @FXML
    private Canvas canvas;

    private GraphicsContext graphicsContext;

    private ShapeFactory shapeFactory;
    private ShapeRepository shapeRepository;
    private Shape selectedShape;

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

        widthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE));
        heightSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE));
        radiusSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE));

        shapeFactory = new ShapeFactory();
        shapeRepository = new ShapeRepository();
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
    public void onCanvasClicked(MouseEvent mouseEvent) {
        if (mode.equalsIgnoreCase("select")) {
            selectedShape = shapeRepository.getSelectedShape(mouseEvent);
            if (selectedShape == null) {
                return;
            }
            colorPicker.setValue((Color) selectedShape.getFill());

            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            shapeRepository.drawShapes(graphicsContext);
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