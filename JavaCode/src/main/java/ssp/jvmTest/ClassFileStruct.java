package ssp.jvmTest;

public class ClassFileStruct {
    private String key = "123";
    public int gcd(int a, int b) {
        if (b == 0) return a;
        else return gcd(b, a % b);
    }

    public static void main(String[] args) {
        ClassFileStruct h1 = new ClassFileStruct();
        int d = h1.gcd(18, 16);
    }
}
