package com.example.simplepaintprogram.model;

import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class ShapeFactory {
    public ShapeEditable getShape(String shape, MouseEvent mouseEvent, List<Spinner<Double>> spinners) {
        if (shape.equalsIgnoreCase("CIRCLE")) {
            return new CircleEditable(mouseEvent.getX(), mouseEvent.getY(), spinners.get(2).getValue());
        } else if (shape.equalsIgnoreCase("RECTANGLE")) {
            return new RectangleEditable(mouseEvent.getX(), mouseEvent.getY(), spinners.get(0).getValue(), spinners.get(1).getValue());
        } else {
            return null;
        }
    }
}
