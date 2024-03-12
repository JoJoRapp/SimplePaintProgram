package com.example.simplepaintprogram.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class RectangleEditable extends Rectangle {

    public boolean wasClicked(MouseEvent mouseEvent) {
        return mouseEvent.getX() >= getX() && mouseEvent.getX() <= getX() + getWidth() &&
                mouseEvent.getY() >= getY() && mouseEvent.getY() <= getY() + getHeight();
    }

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.fillRect(getX(), getY(), getWidth(), getHeight());
        graphicsContext.strokeRect(getX(), getY(), getWidth(), getHeight());
    }
}
