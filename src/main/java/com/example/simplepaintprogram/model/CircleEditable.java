package com.example.simplepaintprogram.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class CircleEditable extends Circle {

    public boolean wasClicked(MouseEvent mouseEvent) {
        return mouseEvent.getX() >= getCenterX() && mouseEvent.getX() <= getCenterX() + getRadius() * 2 &&
                mouseEvent.getY() >= getCenterY() && mouseEvent.getY() <= getCenterY() + getRadius() * 2;
    }

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.fillOval(getCenterX(), getCenterY(), getRadius() * 2, getRadius() * 2);
        graphicsContext.strokeOval(getCenterX(), getCenterY(), getRadius() * 2, getRadius() * 2);
    }
}
