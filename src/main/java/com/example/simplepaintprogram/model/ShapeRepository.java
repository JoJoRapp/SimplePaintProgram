package com.example.simplepaintprogram.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ShapeRepository {

    private final List<ShapeEditable> shapes;

    public ShapeRepository() {
        shapes = new ArrayList<>();
    }

    public void addShape(ShapeEditable shape) {
        shapes.add(shape);
    }

    public void removeShape(ShapeEditable shape) {
        shapes.remove(shape);
    }

    public ShapeEditable getShape(int i) {
        return shapes.get(i);
    }

    public int getSize() {
        return shapes.size();
    }

    public ShapeEditable getSelectedShape(MouseEvent mouseEvent) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            ShapeEditable currentShape = shapes.get(i);
            if (currentShape.wasClicked(mouseEvent)) {
                return currentShape;
            }
        }
        return null;
    }

    public ShapeEditable getSelectedShape(String id) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            ShapeEditable currentShape = shapes.get(i);
            if (currentShape.getId().equalsIgnoreCase(id)) {
                return currentShape;
            }
        }
        return null;
    }

    public void drawShapes(GraphicsContext graphicsContext) {
        for (ShapeEditable s : shapes) {
            graphicsContext.setFill(s.getFill());
            s.draw(graphicsContext);
        }
    }

    public void saveToSvg(File selectedFile) {
        StringBuilder sb = new StringBuilder("<svg xmlns=\"http://www.w3.org/2000/svg\">");

        for (ShapeEditable s : shapes) {
            sb.append(s.toXml());
        }
        sb.append("</svg>");

        try {
            Files.write(selectedFile.toPath(), sb.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
