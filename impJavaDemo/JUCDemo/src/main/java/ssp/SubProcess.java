package ssp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class SubProcess {
    private void getDirFiles(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("扫描到文件: " + file);
//                Path absolutePath = file.toAbsolutePath();
//                if (file.toString().endsWith(".class")) {
//                    String classFullPath = file.toString().replace(File.separatorChar, '.');
//                    int packageIndex = classFullPath.indexOf(packageName);
//                    String classLoadPath = classFullPath.substring(packageIndex, classFullPath.length() - ".class".length());
//                    try {
//                        classList.add(Class.forName(classLoadPath));
//                    } catch (ClassNotFoundException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
    private void Test() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        try {
//            URL resources = SubProcess.class.getResource("");
//            Path path = Paths.get(resources.toURI());
//            getDirFiles(path);
//            System.out.println("当前路径: " + path.toString());
            ProcessBuilder pb = new ProcessBuilder("python", "D:\\research\\repositories\\Demo\\impJavaDemo\\JUCDemo\\src\\main\\java\\ssp\\pidtest.py");
            Process process = pb.start();
            // 启动线程持续读取 stdout，防止阻塞
//            System.out.println("Process class: " + process.getClass().getName());

            System.out.println("等待子进程执行");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int cnt = 0;
            while ((line = reader.readLine()) != null) {
                Field pid = process.getClass().getDeclaredField("pid");
                pid.setAccessible(true);
                String subPid = String.valueOf(pid.getInt(process));
                System.out.println("子进程pid : " + subPid);

                System.out.println("Python 输出: " + line);
                System.out.println("len : " + line.length());
                cnt ++;
            }
            // System.out.println("read cnt : " + cnt);
            // process.waitFor();
            // System.out.println("子进程执行结束");
            // process.waitFor();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws InterruptedException, NoSuchFieldException, IllegalAccessException{
        SubProcess subProcess = new SubProcess();
        subProcess.Test();
    }
}
