package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
    Unicode 本身只是一种字符集，它为每个字符分配一个唯一的数字编号，并没有规定具体的存储方式。UTF-8、UTF-16、UTF-32 都是 Unicode 的编码方式，它们使用不同的字节数来表示 Unicode 字符。例如，UTF-8 :英文占 1 字节，中文占 3 字节。
    实际上都是二进制数
    主要是因为编解码格式的原因
*/


public class ByteIO {
    public static void outputTest(String filePath) {
        try (FileOutputStream output = new FileOutputStream(filePath)) {
            byte[] array = "123你好噜噜噜噜".getBytes();
            output.write(array);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void inputTest(String filePath) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            byte[] bytes = new byte[1024];// 预分配一个缓冲区
            int len;
//            case1: 全读进来，转String(解码为UTF-16
//            while ((len = fileInputStream.read(bytes)) != -1) {
//                String str = new String(bytes, 0, len);
//                System.out.print(str);
//            }

//          case2: 一个一个读，由于中文会被当作两个字节处理，因此会乱码
            while ((len = fileInputStream.read()) != -1) {
                char c = (char) len; // 直接将字节转换为字符
                System.out.print(c); // 不换行输出jJ:w

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        String classPath = ByteIO.class.getClassLoader().getResource("").getPath();
        File classDir = new File(classPath);

        String filePath = classDir.getAbsolutePath() + File.separator + "output.txt";
        System.out.println("filePath: " + filePath);
        outputTest(filePath);
        inputTest(filePath);
    }
}
