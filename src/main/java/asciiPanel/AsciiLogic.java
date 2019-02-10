package asciiPanel;

public interface AsciiLogic {
    /**
     * Do Ascii Window Logic
     * @param arg {@link Argument}
     */
    void accept(Argument arg);

    class Argument {
        public AsciiPanel panel;
        public boolean[] keys;
        public PhantomInt lastKey;
    }
}
