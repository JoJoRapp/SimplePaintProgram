package com.example.simplepaintprogram.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class ShapeRepository {

    private final List<Shape> shapes;

    public ShapeRepository() {
        shapes = new ArrayList<>();
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public Shape getSelectedShape(MouseEvent mouseEvent) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape currentShape = shapes.get(i);
            Class shapeClass = currentShape.getClass();
            if (shapeClass.equals(CircleEditable.class)) {
                CircleEditable circle = (CircleEditable) currentShape;
                if (circle.wasClicked(mouseEvent)) {
                    return currentShape;
                }
            } else if (shapeClass.equals(RectangleEditable.class)) {
                RectangleEditable rectangle = (RectangleEditable) currentShape;
                if (rectangle.wasClicked(mouseEvent)) {
                    return currentShape;
                }
            }
        }
        return null;
    }

    public void drawShapes(GraphicsContext graphicsContext) {
        for (Shape s : shapes) {
            graphicsContext.setFill(s.getFill());
            Class shapeClass = s.getClass();
            if (shapeClass.equals(CircleEditable.class)) {
                CircleEditable circle = (CircleEditable) s;
                circle.draw(graphicsContext);
            } else if (shapeClass.equals(RectangleEditable.class)) {
                RectangleEditable rectangle = (RectangleEditable) s;
                rectangle.draw(graphicsContext);
            }
        }
    }
}
