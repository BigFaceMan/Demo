package Creational;

/**
 * 抽象工厂模式
 *
 * @author zhuzz
 * 2023/8/29 17:21
 */

interface OperatingSystem {
    void run();
}

class WindowsOS implements OperatingSystem {
    @Override
    public void run() {
        System.out.println("Running Windows OS");
    }
}

class LinuxOS implements OperatingSystem {
    @Override
    public void run() {
        System.out.println("Running Linux OS");
    }
}

interface Application {
    void open();
}

class WordApplication implements Application {
    @Override
    public void open() {
        System.out.println("Opening Word Application");
    }
}

class ExcelApplication implements Application {
    @Override
    public void open() {
        System.out.println("Opening Excel Application");
    }
}

interface SoftwareFactory {
    OperatingSystem createOperatingSystem();

    Application createApplication();
}

class WindowsFactory implements SoftwareFactory {
    @Override
    public OperatingSystem createOperatingSystem() {
        return new WindowsOS();
    }

    @Override
    public Application createApplication() {
        return new ExcelApplication();
    }
}

class LinuxFactory implements SoftwareFactory {
    @Override
    public OperatingSystem createOperatingSystem() {
        return new LinuxOS();
    }

    @Override
    public Application createApplication() {
        return new WordApplication();
    }
}

public class AbstractFactory {
    public static void main(String[] args) {
        SoftwareFactory windowsFactory = new WindowsFactory();
        OperatingSystem windowsOS = windowsFactory.createOperatingSystem();
        Application windowsApp = windowsFactory.createApplication();

        windowsOS.run();
        windowsApp.open();

        SoftwareFactory linuxFactory = new LinuxFactory();
        OperatingSystem linuxOS = linuxFactory.createOperatingSystem();
        Application linuxApp = linuxFactory.createApplication();

        linuxOS.run();
        linuxApp.open();
    }
}
