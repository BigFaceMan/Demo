package ssp;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class CodeTest {
    String name = "CodeTest";
    public void exceptionText(String s) {
        System.out.println(this.name);
        System.out.println(s);
        try {
            System.out.println("try block");
        } catch (Exception e) {
            System.out.println("catch block");
        } finally {
            System.out.println("finally block");
        }
    }
    public void invokeTest() {
        CodeTest codeTest = new CodeTest();
        codeTest.exceptionText("123");
    }
    class MyString {
        private final char[] value;
        MyString(String str) {
            this.value = str.toCharArray();
        }
        public void show() {
            for (int i = 0; i < value.length; i++) {
                System.out.print(value[i]);
            }

        }
        public char[] getValue() {
            return value;
        }
        public void setValue(int pos, char value) {
            if (pos < 0 || pos >= this.value.length) {
                throw new IndexOutOfBoundsException("Index: " + pos + ", Length: " + this.value.length);
            }
            this.value[pos] = value;
        }
    }
    @Test
    public void testCode() {
        MyString ms = new MyString("Hello");
        ms.show();
        ms.setValue(0, 'h');
        ms.show();
    }
}
