package com.example.simplepaintprogram.controller;

import com.example.simplepaintprogram.model.CircleEditable;

public class ChangeCircleRadius implements ShapeInteraction {

    private final CircleEditable circle;
    private final double radius;
    private final double oldRadius;

    public ChangeCircleRadius(CircleEditable circle, double radius) {
        this.circle = circle;
        this.radius = radius;
        oldRadius = circle.getRadius();
    }

    @Override
    public void execute() {
        circle.setRadius(radius);
    }

    @Override
    public void undo() {
        circle.setRadius(oldRadius);
    }

    @Override
    public String toString() {
        return "radius " + circle.toXml();
    }
}
