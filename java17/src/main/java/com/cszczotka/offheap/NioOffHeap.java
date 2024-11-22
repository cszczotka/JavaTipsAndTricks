package com.cszczotka.offheap;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

import static java.lang.System.arraycopy;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.file.StandardOpenOption.*;
/**
 * Off heap memory allocation using nio package
 * https://medium.com/everyday-programmer/using-off-heap-memory-in-java-programs-de4fb3e7683f
 */
public class NioOffHeap {
    private MappedByteBuffer mappedByteBuffer;
    private long fileSize;

    /**
     * record class appears in java 14
     */
    record FileHeader() {
        String type() {
            return "simple";
        }

        String version() {
            return "1";
        }

        int headerSize() {
            return 10;
        }

        static FileHeader createHeader() {
            return new FileHeader();
        }
    }

    private void createFile(Path filePath) throws IOException {
        try (FileChannel fileChannel = FileChannel.open(filePath, CREATE, READ, WRITE)) {
            mapFile(fileChannel);
        }
        writeFileHeader();
    }

    private void mapFile(final FileChannel fileChannel) throws IOException {
        mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0L, fileSize);
    }

    private void writeFileHeader() {

        FileHeader header = FileHeader.createHeader();
        int headerLength = header.headerSize();
        byte[] headerBytes = new byte[headerLength];
        arraycopy(header.type().getBytes(US_ASCII), 0, headerBytes, 0, header.type().length());
        arraycopy(header.version().getBytes(US_ASCII), 0, headerBytes, header.type().length(), header.version().length());
        mappedByteBuffer.put(headerBytes);
    }

    public static void main(String[] args) {
        try {
            NioOffHeap nioOffHeap = new NioOffHeap();
            nioOffHeap.createFile(Path.of("c:/tmp/test.mmap"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
