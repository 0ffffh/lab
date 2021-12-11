package com.k0s.io;

import java.io.*;


public class FileManager {


    // public static int countFiles(String path) - принимает путь к папке,
    // возвращает количество файлов в папке и всех подпапках по пути
    public static int countFiles(String path) throws IOException {
        checkPathSource(path);
        File sourcePath = new File(path);
        int count;

        if(sourcePath.isDirectory()){
            count = getCountFiles(sourcePath);
            System.out.println("Total " + count + " files.");
            return count;
        } else {
            System.out.println(sourcePath + " is not a folder");
            return -1;
        }
    }

    // public static int countDirs(String path) - принимает путь к папке,
    // возвращает количество папок в папке и всех подпапках по пути
    public static int countDirs(String path) throws IOException {
        checkPathSource(path);
        File sourcePath = new File(path);
        int count;

        if(sourcePath.isDirectory()){
            count = getCountDirs(sourcePath);
            System.out.println("Total " + count + " folders.");
            return count;
        } else {
            System.out.println(sourcePath + " is not a folder");
            return -1;
        }
    }


    //метод по копированию папок и файлов.
    //Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться копирование.
    public static void copy(String from, String to) throws IOException {
        checkPathSource(from);
        checkPathNull(to);
        File source = new File(from);
        if (source.isFile()) {
            copyFileUsingBuff(from, to);
        } else {
            copyRecursiveFolder(from, to);
        }
    }

    //метод по перемещению папок и файлов.
    //Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться копирование.
    public static void move(String from, String to) throws IOException {
        copy(from, to);
        remove(from);
    }



    public static void remove(String path) throws IOException {
        checkPathSource(path);
        File rootPath = new File (path);
        if(rootPath.isDirectory()){
            for(File file : rootPath.listFiles()){
                remove(file.getAbsolutePath());
            }
        }
        rootPath.delete();
    }


    public static long getSize(String path) throws IOException {
        long size = 0;
        checkPathSource(path);

        File file = new File(path);
        if (file.isFile()) {
            size += file.length();
        } else {
            for (File files : file.listFiles()) {
                size += getSize(files.getAbsolutePath());
            }
        }
        return size;
    }


    private static int getCountFiles(File sourcePath){
        int count = 0;
        for (File file : sourcePath.listFiles()) {
            if (file.isDirectory()) {
                count += getCountFiles(file);
            } else if (file.isFile()) {
                count++;
            }
        }
        return count;
    }

    private static int getCountDirs(File sourcePath) {
        int count = 0;
            for (File file : sourcePath.listFiles()) {
                if (file.isDirectory() && file.canRead()) {
                    count++;
                    count += getCountFiles(file);
                } else if (file.isDirectory() && !file.canRead()) {
                    count++;
                }
            }
        return count;
    }

    private static void copyRecursiveFolder(String from, String to) {
        File source = new File(from);
        File destination = new File(to);
        destination.mkdir();
        for (File path : source.listFiles()) {
            if (path.isDirectory() && path.canRead()) {
                File destinationFolder = new File(destination.getAbsolutePath(), path.getName());
                destinationFolder.mkdir();
                copyRecursiveFolder(path.getAbsolutePath(), destinationFolder.getAbsolutePath());
            }
            if (path.isFile() && path.canRead()) {
                File destinationFile = new File(destination.getAbsolutePath(), path.getName());
//                    try {
//                        destinationFile.createNewFile();
//                    } catch (IOException e) {
//                        System.out.println("Can't create file <" + to + "> already exist" + e);
//                        e.printStackTrace();
//                    }
                copyFileUsingBuff(path.getAbsolutePath(), destinationFile.getAbsolutePath());
            }
        }
    }

    private static void copyFileUsingBuff(String from, String to) {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(from));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(to))){

            int length;
            byte[] buff = new byte[4096];
            while ((length = bufferedInputStream.read(buff)) != -1){
                bufferedOutputStream.write(buff,0, length);
            }
            bufferedOutputStream.flush();

        } catch (IOException e) {
            System.out.println("IO exception: can't create file");
            e.printStackTrace();
        }
    }

    private static void checkPathSource(String path) throws IOException {
        checkPathNull(path);
        File file = new File(path);
        if(!file.exists()) {
            throw new IOException("No such file or directory <"  + path + ">");

        }
        if(!file.canRead()) {
            throw new IOException("Can't read, no access permissions to <"  + path + ">");
        }

    }

    private static void checkPathNull(String path)  {
        if (path == null) {
            throw new IllegalArgumentException ("Invalid operation path can't be <" + path + ">");
        }

    }

}
