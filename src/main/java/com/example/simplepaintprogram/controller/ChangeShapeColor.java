package com.example.simplepaintprogram.controller;

import com.example.simplepaintprogram.model.ShapeEditable;
import javafx.scene.paint.Paint;

public class ChangeShapeColor implements ShapeInteraction {

    private final ShapeEditable shape;
    private final Paint paint;
    private final Paint oldPaint;

    public ChangeShapeColor(ShapeEditable shape, Paint paint) {
        this.shape = shape;
        this.paint = paint;
        if (shape != null)
            oldPaint = shape.getFill();
        else
            oldPaint = Paint.valueOf("black");
    }

    @Override
    public void execute() {
        shape.setFill(paint);
    }

    @Override
    public void undo() {
        shape.setFill(oldPaint);
    }

    @Override
    public String toString() {
        return "color " + shape.toXml();
    }
}
