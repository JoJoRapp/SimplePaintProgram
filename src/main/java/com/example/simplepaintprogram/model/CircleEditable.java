package com.example.simplepaintprogram.model;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class CircleEditable extends Circle {
    public CircleEditable(double x, double y, double radius, Paint paint) {
        super(x, y, radius, paint);
    }

    public boolean wasClicked(MouseEvent mouseEvent) {
        return mouseEvent.getX() >= getCenterX() - getRadius() && mouseEvent.getX() <= getCenterX() + getRadius() &&
                mouseEvent.getY() >= getCenterY() - getRadius() && mouseEvent.getY() <= getCenterY() + getRadius();
    }
}
