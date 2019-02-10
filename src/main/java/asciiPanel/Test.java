package asciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Test {
    public static void main(String[] args) {
        AsciiWindow window = new AsciiWindow();
        Point p = new Point();
        window.setLogic(arg -> {
            arg.panel.write("@", p.x, p.y);
            int key = arg.lastKey.get();
            if (key == KeyEvent.VK_LEFT) {
                p.x -= 1;
            } else if (key == KeyEvent.VK_RIGHT) {
                p.x += 1;
            } else if (key == KeyEvent.VK_UP) {
                p.y -= 1;
            } else if (key == KeyEvent.VK_DOWN) {
                p.y += 1;
            }
        });
        window.startLoop();
    }
}
