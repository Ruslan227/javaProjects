package info.kgeorgiy.ja.Aliev.walk;

import java.io.*;
import java.nio.file.*;

public class Walk {
    private static long h = 0;
    private static final long FIRST_FOUR_BITS_LONG = 0xff00_0000_0000_0000L;
    private static final int BUFFER_SIZE = 80192;

    public static void main(String[] args) {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            System.out.println("incorrect input files - expected two files");
            return;
        }
        launchWalk(args[0], args[1]);
    }

    private static void launchWalk(String inputFileName, String outputFileName) {

        /**
         *  Проверка на существование input файла
         */
        Path inputPath;
        try {
            inputPath = Path.of(inputFileName);
        } catch (InvalidPathException e) {
            System.out.println("invalid input path");
            e.printStackTrace();
            return;
        }

        try {
            if (!Files.exists(inputPath)) {
                System.out.println("No such file or directory found (input): " + inputPath.toString());
                return;
            }
        } catch (SecurityException e) {
            System.out.println("can't access to input file");
            e.printStackTrace();
            return;
        }


//        -------------------------------------------

        /**
         *    если родительская
         *        директория выходного файла не существует, то соответствующий путь надо создать
         *        создание файла output
         */

        Path outputPath;
        try {
            outputPath = Path.of(outputFileName);
        } catch (InvalidPathException e) {
            System.out.println("invalid output path");
            e.printStackTrace();
            return;
        }
        try {
            if (Files.notExists(outputPath)) {
                try {
                    Files.createDirectories(outputPath.getParent());
                    Files.createFile(outputPath);
                } catch (IOException | UnsupportedOperationException | SecurityException e) {
                    System.out.println("can't create directory of output file or output file");
                    e.printStackTrace();
                    return;
                }
            }
        } catch (SecurityException e) {
            System.out.println("can't access to output file");
            e.printStackTrace();
            return;
        }

        /**
         *  проверка на валидность output
         */
        try {
            if (!Files.isRegularFile(outputPath)) {
                System.out.println("error: output file is invalid");
                return;
            }
        } catch (SecurityException e) {
            System.out.println("output file is invalid");
            e.printStackTrace();
            return;
        }


        /**
         * Проверка на совпадение файлов
         */

        try {
            if (Files.isSameFile(inputPath, outputPath)) {
                System.out.println("input and output files are equal");
                return;
            }
        } catch (IOException | SecurityException e) {
            System.out.println("can't compare files");
            e.printStackTrace();
            return;
        }

//        -------------------------------------------

        /**
         *    Чтение input файла и запуск countFileHash() от всех файлов
         */

        try (BufferedReader reader = Files.newBufferedReader(inputPath)) {
            try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
                String line;
                byte[] b = new byte[BUFFER_SIZE];
                while ((line = reader.readLine()) != null) {
                    countFileHash(line, writer, b);
                }
            } catch (IOException e) {
                System.out.println("can't write output file");
                e.printStackTrace();
            }
        } catch (IOException | SecurityException e) {
            System.out.println("can't read input file");
            e.printStackTrace();
        }
    }


    // считает хеш + записывает в output файл
    private static void countFileHash(String fileName, BufferedWriter writer, byte[] b) {
        try (InputStream reader = Files.newInputStream(Path.of(fileName))) { // открываем на чтение наш файлик
            int size;

            while ((size = reader.read(b)) != -1) {    // читаем наш файлик
                pjwHashGenerator(b, size);         // запуск count hash
            }
            writeHash(writer, fileName, h);
        } catch (IOException | InvalidPathException e) {
            System.out.println("can't read file from input file");
            e.printStackTrace();
            writeHash(writer, fileName, 0);
        }
    }

    private static void pjwHashGenerator(final byte[] bytes, final int size) {
        // изменяет переменную h
        // long l1 = Long.parseUnsignedLong("12345678901234567890");
        for (int i = 0; i < size; i++) {
            h = (h << 8) + (bytes[i] & 0xff);
            final long test = h & FIRST_FOUR_BITS_LONG;
            if (test != 0) {
                h ^= test >> 48;
                h &= ~test;
            }
        }
    }

    private static void writeHash(BufferedWriter writer, String fileName, long hash) {
        h = hash;
        String s = String.format("%016x", h) + " " + fileName + "\n";
        try {
            writer.write(s);    // записываем в output 0 hash
        } catch (IOException e) {
            System.out.println("can't write output file");
            e.printStackTrace();
        }
        h = 0;
    }
}


