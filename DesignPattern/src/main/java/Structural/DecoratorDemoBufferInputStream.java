import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

class BufferedFileInputStream extends InputStream {
    private final byte[] buffer = new byte[8192];
    private int position = -1;
    private int capacity = -1;

    private final InputStream inputStream;

    BufferedFileInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public int read() throws IOException {
        if (buffCanRead()) {
            return readFromBuff();
        }
        refreshBuffer();
        if (buffCanRead()) {
            return readFromBuff();
        }
        return -1;
    }
    public void refreshBuffer() throws IOException {
        capacity = this.inputStream.read(buffer);
        position = 0;
    }
    public int readFromBuff() {
        return buffer[position++] & 0xFF;
    }
    public boolean buffCanRead() {
        if (capacity == -1) {
            return false;
        }
        if (position == capacity) {
            return false;
        }
        return true;
    }

}
public class DecoratorDemoBufferInputStream {
    public static void main(String[] args) {
        File file = new File("D:/research/computerbase/Vim实用技巧第2版.pdf");

        long l = Instant.now().toEpochMilli();
        try (InputStream fileInputStream = new BufferedFileInputStream(new FileInputStream(file))) {
//        try (InputStream fileInputStream = new FileInputStream(file)) {
            while (true) {
                int readValue = fileInputStream.read();
                if (readValue == -1) {
                    break;
                }
            }
            System.out.println("用时:" + (Instant.now().toEpochMilli() - l) + "毫秒");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
