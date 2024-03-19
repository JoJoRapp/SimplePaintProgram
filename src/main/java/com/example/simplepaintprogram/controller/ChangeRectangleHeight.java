package com.example.simplepaintprogram.controller;

import com.example.simplepaintprogram.model.RectangleEditable;

public class ChangeRectangleHeight implements ShapeInteraction {

    private final RectangleEditable rectangle;
    private final double height;
    private final double oldHeight;

    public ChangeRectangleHeight(RectangleEditable rectangle, double height) {
        this.rectangle = rectangle;
        this.height = height;
        oldHeight = rectangle.getHeight();
    }

    @Override
    public void execute() {
        rectangle.setHeight(height);
    }

    @Override
    public void undo() {
        rectangle.setHeight(oldHeight);
    }
}
