package com.example.simplepaintprogram.controller;

import com.example.simplepaintprogram.model.RectangleEditable;

public class ChangeRectangleHeight implements ShapeInteraction {

    private final RectangleEditable rectangle;
    private final double height;

    public ChangeRectangleHeight(RectangleEditable rectangle, double height) {
        this.rectangle = rectangle;
        this.height = height;
    }

    @Override
    public void execute() {
        rectangle.setHeight(height);
    }

    @Override
    public void undo() {

    }
}
