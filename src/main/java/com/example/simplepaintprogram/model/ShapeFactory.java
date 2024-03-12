package com.example.simplepaintprogram.model;

import javafx.scene.shape.Shape;

public class ShapeFactory {
    public Shape getShape(String shape) {
        if (shape.equalsIgnoreCase("CIRCLE")) {
            return new CircleEditable();
        } else if (shape.equalsIgnoreCase("RECTANGLE")) {
            return new RectangleEditable();
        } else {
            return null;
        }
    }
}
