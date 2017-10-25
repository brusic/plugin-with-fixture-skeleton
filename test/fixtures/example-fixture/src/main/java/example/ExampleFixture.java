package example;

import java.lang.management.ManagementFactory;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;

public class ExampleFixture {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting ExampleFixture with args " + Arrays.asList(args));

        if (args.length != 1) {
            throw new IllegalArgumentException("ExampleFixture <logDirectory>");
        }

        Path dir = Paths.get(args[0]);
        writeRequiredTestFiles(dir);

        // the fixture thread is required to exist during the integration tests
        // wait forever, until you kill me
        Thread.sleep(Long.MAX_VALUE);
    }

    /*
     * Two files are required to exist for any AntFixture: pid and ports
     */
    private static void writeRequiredTestFiles(Path dir) throws Exception {
        // write pid file
        Path tmp = Files.createTempFile(dir, null, null);
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        Files.write(tmp, Collections.singleton(pid));
        Files.move(tmp, dir.resolve("pid"), StandardCopyOption.ATOMIC_MOVE);

        // write port file
        tmp = Files.createTempFile(dir, null, null);
        InetSocketAddress bound = new InetSocketAddress(InetAddress.getLoopbackAddress(), 0);
        if (bound.getAddress() instanceof Inet6Address) {
            Files.write(tmp, Collections.singleton("[" + bound.getHostString() + "]:" + bound.getPort()));
        } else {
            Files.write(tmp, Collections.singleton(bound.getHostString() + ":" + bound.getPort()));
        }
        Files.move(tmp, dir.resolve("ports"), StandardCopyOption.ATOMIC_MOVE);
    }
}
