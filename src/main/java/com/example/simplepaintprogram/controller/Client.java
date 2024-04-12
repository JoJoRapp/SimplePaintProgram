package com.example.simplepaintprogram.controller;

import com.example.simplepaintprogram.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private boolean connected;

    private PrintWriter writer;

    private final InteractionInvoker interactionSender;

    private final ShapeFactory shapeFactory;

    private final ShapeRepository shapeRepository;

    private final GraphicsContext graphicsContext;

    private final Canvas canvas;

    public Client(InteractionInvoker interactionSender, ShapeFactory shapeFactory, ShapeRepository shapeRepository, GraphicsContext graphicsContext, Canvas canvas) {
        this.connected = false;
        this.interactionSender = interactionSender;
        this.shapeFactory = shapeFactory;
        this.shapeRepository = shapeRepository;
        this.graphicsContext = graphicsContext;
        this.canvas = canvas;

    }

    public boolean isConnected() {
        return connected;
    }

    @Override
    public void run() {
        try( Socket socket = new Socket("localhost", 8000) ) {
            connected = true;
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            InputStream input = socket.getInputStream();

            Thread readerThread = new Thread(() -> handleInput(input));
            readerThread.start();

            while (true);
        }
        catch (IOException e) {
            connected = false;
            e.printStackTrace();
        }
    }

    public void sendCommand(String command) {
        writer.println(command);
    }

    private void handleInput(InputStream input) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = "";
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line.matches("\\S+\\s\\S+\\s<\\S+(\\s\\S+=\"\\S+\")+/>")) {
                String[] args = line.split("(\"/>| <|=\"|\" | )");
                if (!args[0].equalsIgnoreCase("[you]")) {
                    if (args[1].equalsIgnoreCase("add")) {
                        ShapeEditable shape;
                        Color color = Color.web(args[args.length - 3]);
                        if (args[2].equalsIgnoreCase("CIRCLE")) {
                            double radius = Double.parseDouble(args[8]);
                            double x = Double.parseDouble(args[4]) - radius;
                            double y = Double.parseDouble(args[6]) - radius;
                            shape = shapeFactory.getShape("circle", x, y, 0, 0, radius, args[args.length - 1]);
                        }
                        else if (args[2].equalsIgnoreCase("RECT")) {
                            double width = Double.parseDouble(args[8]);
                            double height = Double.parseDouble(args[10]);
                            double x = Double.parseDouble(args[4]);
                            double y = Double.parseDouble(args[6]);
                            shape = shapeFactory.getShape("rectangle", x, y, width, height, 0, args[args.length - 1]);
                        }
                        else {
                            return;
                        }

                        shape.setFill(color);
                        interactionSender.runCommand(new AddShape(shape, shapeRepository));
                        graphicsContext.setFill(color);
                        shape.draw(graphicsContext);
                    }
                    else {
                        ShapeEditable incomingShape = shapeRepository.getSelectedShape(args[args.length - 1]);
                        switch (args[1]) {
                            case "color" ->
                                    interactionSender.runCommand(new ChangeShapeColor(incomingShape, Color.web(args[args.length - 3])));
                            case "width" ->
                                    interactionSender.runCommand(new ChangeRectangleWidth((RectangleEditable) incomingShape, Double.parseDouble(args[8])));
                            case "height" ->
                                    interactionSender.runCommand(new ChangeRectangleHeight((RectangleEditable) incomingShape, Double.parseDouble(args[10])));
                            case "radius" ->
                                    interactionSender.runCommand(new ChangeCircleRadius((CircleEditable) incomingShape, Double.parseDouble(args[8])));
                        }
                        updateCanvas();
                    }
                }
            } else if (line.matches("\\S+\\s\\S+")) {
                String[] args = line.split(" ");
                if (!args[0].equalsIgnoreCase("[you]")) {
                    if (args[1].equalsIgnoreCase("/undo")) {
                        interactionSender.undo();
                    }
                    else if (args[1].equalsIgnoreCase("/redo")) {
                        interactionSender.redo();
                    }
                    updateCanvas();
                }
            }

        }
    }

    private void updateCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        shapeRepository.drawShapes(graphicsContext);
    }
}
