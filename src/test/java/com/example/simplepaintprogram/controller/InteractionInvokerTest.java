package com.example.simplepaintprogram.controller;

import com.example.simplepaintprogram.model.CircleEditable;
import com.example.simplepaintprogram.model.ShapeEditable;
import com.example.simplepaintprogram.model.ShapeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InteractionInvokerTest {

    InteractionInvoker invoker = new InteractionInvoker();
    ShapeRepository shapeRepository = new ShapeRepository();

    ShapeEditable shape = new CircleEditable(10, 5, 20);

    @Test
    void testRunCommandAddShape() {
        invoker.runCommand(new AddShape(shape, shapeRepository));
        Assertions.assertEquals(shape, shapeRepository.getShape(0));
    }

    @Test
    void testUndo() {
        invoker.runCommand(new AddShape(shape, shapeRepository));
        invoker.undo();
        Assertions.assertEquals(0, shapeRepository.getSize());
    }

    @Test
    void testRedo() {
        invoker.runCommand(new AddShape(shape, shapeRepository));
        invoker.undo();
        invoker.redo();
        Assertions.assertEquals(shape, shapeRepository.getShape(0));
    }
}
