package com.kiloalphaoscar.main;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    public static final int WIDTH = 640;
    public static final int HEIGHT = WIDTH / 12 * 9; //Ask me why it's like this

    private Thread thread;
    private boolean running = false;

    public Game () {
        new Window(WIDTH, HEIGHT,"Game Title", this);
    }

    public synchronized void start () {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop () {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This run loop is a verified production level game loop that
     * is even used in games like Minecraft
     */
    public void run () {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick () {

    }

    private void render () {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3); //This is how many buffers are created
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.green);
        g.fillRect(0,0, WIDTH, HEIGHT);

        g.dispose();
        bs.show();
    }

    public static void main (String args[]) {
        new Game();
    }
}
