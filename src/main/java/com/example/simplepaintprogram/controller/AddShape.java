package com.example.simplepaintprogram.controller;

import com.example.simplepaintprogram.model.ShapeEditable;
import com.example.simplepaintprogram.model.ShapeRepository;

public class AddShape implements ShapeInteraction {

    private final ShapeEditable shape;
    private final ShapeRepository shapeRepository;

    public AddShape(ShapeEditable shape, ShapeRepository shapeRepository) {
        this.shape = shape;
        this.shapeRepository = shapeRepository;
    }

    @Override
    public void execute() {
        shapeRepository.addShape(shape);
    }

    @Override
    public void undo() {
        shapeRepository.removeShape(shape);
    }
}
