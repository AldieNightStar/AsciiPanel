package asciiPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AsciiWindow {
    private AsciiPanel panel;
    private boolean[] keys = new boolean[256];
    private int lastKey = 0;
    private JFrame jFrame;
    private AsciiLogic logic;
    private boolean loop = false;

    // For arguments
    private AsciiLogic.Argument argument = new AsciiLogic.Argument();

    /**
     * Create Ascii Panel AsciiWindow
     *
     * @throws HeadlessException
     */
    public AsciiWindow() throws HeadlessException {
        this("AsciiPanel");
    }

    /**
     * Create Ascii Panel AsciiWindow
     *
     * @param title AsciiWindow title
     * @throws HeadlessException
     */
    public AsciiWindow(String title) throws HeadlessException {
        this(title, 80, 40);
    }

    /**
     * Create Ascii Panel AsciiWindow
     *
     * @param title AsciiWindow title
     * @param w     symbol width
     * @param h     symbol height
     */
    public AsciiWindow(String title, int w, int h) {
        this(title, w, h, null);
    }

    /**
     * Create Ascii Panel AsciiWindow
     *
     * @param title AsciiWindow title
     * @param w     symbol width
     * @param h     symbol height
     * @param font  Font for Ascii Panel
     */
    public AsciiWindow(String title, int w, int h, AsciiFont font) {
        jFrame = new JFrame(title);
        panel = new AsciiPanel(w, h);
        if (font != null) panel.setAsciiFont(font);
        initWindow();
    }

    private void initWindow() {
        jFrame.getContentPane().add(panel);
        jFrame.setSize(0, 0);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                keys[keyCode] = true;
                lastKey = keyCode;
            }
            public void keyReleased(KeyEvent e) {
                keys[e.getKeyCode()] = false;
            }
        });
    }

    /**
     * get Ascii Panel to play with :)
     *
     * @return {@link AsciiPanel}
     */
    public AsciiPanel getPanel() {
        return panel;
    }

    /**
     * Get JFrame window
     *
     * @return {@link JFrame}
     */
    public JFrame getJFrame() {
        return jFrame;
    }

    /**
     * Set new Logic for Ascii Panel Loop
     *
     * @param logic {@link AsciiLogic}
     */
    public void setLogic(AsciiLogic logic) {
        this.logic = logic;
    }

    /**
     * Call Ascii Panel Logic
     */
    private void callLogic() {
        panel.clear();
        argument.keys = keys;
        argument.panel = panel;
        argument.lastKey = () -> {
            int k = lastKey;
            lastKey = 0;
            return k;
        };
        if (logic != null) logic.accept(argument);
        panel.repaint();
    }

    /**
     * Start Logic Loop
     */
    public void startLoop() {
        if (loop) return;
        loop = true;
        Thread t = new Thread(() -> {
            while (loop) {
                try {
                    Thread.sleep(33);
                } catch (Exception e) {
                    e.printStackTrace();
                    loop = false;
                }
                callLogic();
            }
        });
        t.setDaemon(true);
        t.start();
    }

    /**
     * Stop Logic Loop thread
     */
    public void stopLoop() {
        loop = false;
    }
}
