package com.example.simplepaintprogram.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class PaintController {

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button circleButton;

    @FXML
    private Button rectangleButton;

    @FXML
    private Canvas canvas;

    GraphicsContext graphicsContext;

    private String mode = "rect";

    @FXML
    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        colorPicker.setOnAction(e -> graphicsContext.setFill(colorPicker.getValue()));
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

    public void onCanvasClicked(MouseEvent mouseEvent) {
        if (mode.equals("rect")) {
            Rectangle rectangle = new Rectangle(mouseEvent.getX(), mouseEvent.getY(), 50, 60);
            rectangle.setFill(graphicsContext.getFill());
            graphicsContext.fillRect(rectangle.getX(), rectangle.getY(), 50, 60);
            graphicsContext.strokeRect(rectangle.getX(), rectangle.getY(), 50, 60);
        } else if (mode.equals("circle")) {
            Circle circle = new Circle(mouseEvent.getX(), mouseEvent.getY(), 25, graphicsContext.getFill());
            graphicsContext.fillOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius() * 2, circle.getRadius() * 2);
            graphicsContext.strokeOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius() * 2, circle.getRadius() * 2);
        }
    }
}