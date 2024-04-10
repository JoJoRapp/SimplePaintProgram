package com.example.simplepaintprogram.model;

public class ShapeFactory {
    public ShapeEditable getShape(String shape, double x, double y, double width, double height, double radius) {
        if (shape.equalsIgnoreCase("CIRCLE")) {
            return new CircleEditable(x, y, radius);
        } else if (shape.equalsIgnoreCase("RECTANGLE")) {
            return new RectangleEditable(x, y, width, height);
        } else {
            return null;
        }
    }

    public ShapeEditable getShape(String shape, double x, double y, double width, double height, double radius, String id) {
        if (shape.equalsIgnoreCase("CIRCLE")) {
            return new CircleEditable(x, y, radius, id);
        } else if (shape.equalsIgnoreCase("RECTANGLE")) {
            return new RectangleEditable(x, y, width, height, id);
        } else {
            return null;
        }
    }
}
