package com.example.simplepaintprogram.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
            Class<? extends Shape> shapeClass = currentShape.getClass();
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
            Class<? extends Shape> shapeClass = s.getClass();
            if (shapeClass.equals(CircleEditable.class)) {
                CircleEditable circle = (CircleEditable) s;
                circle.draw(graphicsContext);
            } else if (shapeClass.equals(RectangleEditable.class)) {
                RectangleEditable rectangle = (RectangleEditable) s;
                rectangle.draw(graphicsContext);
            }
        }
    }

    public void saveToSvg(File selectedFile) {
        StringBuilder sb = new StringBuilder("<svg xmlns=\"http://www.w3.org/2000/svg\">");

        for (Shape s : shapes) {
            Class<? extends Shape> shapeClass = s.getClass();
            if (shapeClass.equals(CircleEditable.class)) {
                CircleEditable circle = (CircleEditable) s;
                sb.append("<circle cx=\"");
                sb.append(circle.getCenterX() + circle.getRadius());
                sb.append("\" cy=\"");
                sb.append(circle.getCenterY() + circle.getRadius());
                sb.append("\" r=\"");
                sb.append(circle.getRadius());
                sb.append("\" stroke=\"black\" fill=\"");
                sb.append(circle.getFill());
                sb.replace(sb.lastIndexOf("0x"), sb.lastIndexOf("0x") + 2, "#");
                sb.append("\"/>");
            } else if (shapeClass.equals(RectangleEditable.class)) {
                RectangleEditable rectangle = (RectangleEditable) s;
                sb.append("<rect x=\"");
                sb.append(rectangle.getX());
                sb.append("\" y=\"");
                sb.append(rectangle.getY());
                sb.append("\" width=\"");
                sb.append(rectangle.getWidth());
                sb.append("\" height=\"");
                sb.append(rectangle.getHeight());
                sb.append("\" stroke=\"black\" fill=\"");
                sb.append(rectangle.getFill());
                sb.replace(sb.lastIndexOf("0x"), sb.lastIndexOf("0x") + 2, "#");
                sb.append("\"/>");
            }
        }
        sb.append("</svg>");

        try {
            Files.write(selectedFile.toPath(), sb.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
