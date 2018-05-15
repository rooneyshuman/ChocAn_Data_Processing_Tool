package chocan;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.function.Consumer;

public class IOTestUtils {

    /**
     *
     * @param inputStream
     * @return
     */
    public static String toString(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream result = new ByteArrayOutputStream();
        drain(inputStream, result::write);
        return result.toString(StandardCharsets.UTF_8.name());
    }

    public static void drain(final InputStream inputStream) throws IOException {
        drain(inputStream, null);
    }

    public static void drain(final InputStream inputStream, final ReadStreamCallback callback) throws IOException {
        byte[] buffer = new byte[4096];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            if (callback != null) {
                callback.write(buffer, 0, length);
            }
        }
    }

    public interface ReadStreamCallback {
        void write(byte[] buffer, int offset, int length);
    }

    public static boolean configurePipes(final Consumer<Scanner> inputConsumer, final PipesCreatedCallback callback) throws IOException, InterruptedException {
        return configurePipes(inputConsumer, callback, 0);
    }

    /**
     *
     * @param inputConsumer
     * @param callback
     */
    public static boolean configurePipes(final Consumer<Scanner> inputConsumer, final PipesCreatedCallback callback, final int timeout) throws IOException, InterruptedException {
        final InputStream oldIn = System.in;
        final PrintStream oldOut = System.out;
        final PipedInputStream in = new PipedInputStream();
        final PipedOutputStream out = new PipedOutputStream();
        try (PipedOutputStream toInput = new PipedOutputStream(in); PipedInputStream fromOutput = new PipedInputStream(out)) {
            System.setIn(in);
            System.setOut(new PrintStream(out));
            final Thread thread = new Thread(() -> {
                try (final Scanner stdin = new Scanner(System.in)) {
                    inputConsumer.accept(stdin);
                }
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.setDaemon(true);
            thread.start();
            callback.pipesCreated(toInput, fromOutput);
            thread.join(timeout);
            return !thread.isAlive();
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
    }

    public interface PipesCreatedCallback {

        /**
         *
         * @param toInput
         * @param fromOutput
         */
        void pipesCreated(final OutputStream toInput, final InputStream fromOutput) throws IOException;

    }

}
