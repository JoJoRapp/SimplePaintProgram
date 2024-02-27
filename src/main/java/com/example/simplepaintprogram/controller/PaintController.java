package com.example.simplepaintprogram.controller;

import com.example.simplepaintprogram.model.CircleEditable;
import com.example.simplepaintprogram.model.RectangleEditable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;

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

    private String mode = "rect";

    @FXML
    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        colorPicker.setOnAction(e -> graphicsContext.setFill(colorPicker.getValue()));
        widthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE));
        heightSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE));
        radiusSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE));
    }

    @FXML
    public void onRectangleButtonClick() {
        rectangleButton.setDisable(true);
        circleButton.setDisable(false);
        mode = "rect";
    }

    @FXML
    public void onCircleButtonClick() {
        circleButton.setDisable(true);
        rectangleButton.setDisable(false);
        mode = "circle";
    }

    public void onSelectButtonClick(ActionEvent actionEvent) {

    }

    public void onCanvasClicked(MouseEvent mouseEvent) {
        if (mode.equals("rect")) {
            RectangleEditable rectangle = new RectangleEditable(mouseEvent.getX(), mouseEvent.getY(), 50, 60);
            rectangle.setFill(graphicsContext.getFill());
            graphicsContext.fillRect(rectangle.getX(), rectangle.getY(), 50, 60);
            graphicsContext.strokeRect(rectangle.getX(), rectangle.getY(), 50, 60);
        } else if (mode.equals("circle")) {
            CircleEditable circle = new CircleEditable(mouseEvent.getX(), mouseEvent.getY(), 25, graphicsContext.getFill());
            graphicsContext.fillOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius() * 2, circle.getRadius() * 2);
            graphicsContext.strokeOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius() * 2, circle.getRadius() * 2);
        }
    }
}