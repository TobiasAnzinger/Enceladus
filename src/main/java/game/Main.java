package game;

import org.lwjgl.glfw.GLFW;

import engine.Input;
import engine.Window;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;

public class Main implements Runnable {
    public Thread game;
    public Window window;
    public final int WIDTH = 1920, HEIGHT = 1080;

    int red = 0;
    int green = 0;
    int blue = 125;

    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

    public void init() {
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable
        window = new Window(WIDTH, HEIGHT, "Game");
        window.create();
    }

    public void run() {
        init();
        while (!window.shouldClose()) {
            GL.createCapabilities();
            glClearColor(100.0f, 100.0f, 100.0f, 0.0f);

            update();
            render();
            if (Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) return;
        }
        window.destroy();
    }

    private void update() {
        window.update();
//        System.out.println("X: " + Input.getMouseX() + ", Y: " + Input.getMouseY());
        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) System.out.println("M1 is down");
    }

    private void render() {
        window.swapBuffers();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}