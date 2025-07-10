package org.example;

import java.io.BufferedReader;
import java.io.IOException;

public class ProcessDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
        String[] cmds = {
                "cmd.exe", "/c",
                "conda activate mcp" + " && python  D:\\research\\repositories\\Demo\\impJavaDemo\\JVMDemo\\src\\main\\java\\org\\example\\printDemo.py"
        };

        ProcessBuilder builder = new ProcessBuilder(cmds);
        builder.inheritIO();
        Process process = builder.start();
        process.waitFor();

//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(process.getInputStream()));
//
//        String line;
//        while ((line = reader.readLine()) != null) {
//            System.out.println(line); // 输出子进程的输出
//        }
//        process.waitFor();
    }
}
