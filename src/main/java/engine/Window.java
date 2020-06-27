package engine;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;

public class Window {
    private int width, height;
    private String title;
    private long window;
    public int frames;
    public static long time;
    public Input input;

    String r = "r:";
    String g = " g:";
    String b = " b:";

    float red = 0;
    float green = 0;
    float blue = 125;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create() {
        if (!GLFW.glfwInit()) {
            System.err.println("ERROR: GLFW wasn't initializied");
            return;
        }


        input = new Input();
//        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        window = GLFW.glfwCreateWindow(width, height, title, glfwGetPrimaryMonitor(), 0);

        if (window == 0) {
            System.err.println("ERROR: Window wasn't created");
            return;
        }



        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(),(videoMode.width() - width) / 2, (videoMode.height() - height) / 2, width ,height, 60 );

//        GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        GLFW.glfwMakeContextCurrent(window);

        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());

        GLFW.glfwShowWindow(window);

        GLFW.glfwSwapInterval(1);

        glfwMakeContextCurrent(window);
        time = System.currentTimeMillis();
    }

    public void update() {
        frames++;
        if (System.currentTimeMillis() > time + 1000) {
            GLFW.glfwSetWindowTitle(window, title + " | FPS: " + frames);
            time = System.currentTimeMillis();
            System.out.println(frames);
            frames = 0;
        }

//        if(red > 240){
//            red--;
//        } else if(red < 10){
//            red++;
//        }
//        glClearColor(red, green, blue, 0.0f);
//        System.out.println(r + red + g + green + b + blue);

        glfwPollEvents();
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void destroy() {
        input.destroy();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}