package minecraft.client.util;

import java.util.Arrays;
import java.util.List;

public class SystemUtils {

    public static String getArquitectura() {
        List<String> arquitecturas64Bits = Arrays.asList(
                "amd64", "x86_64", "ia64", "arm64", "aarch64", "ppc64", "sparcv9");

        String arquitectura = System.getProperty("os.arch");
        if (arquitecturas64Bits.contains(arquitectura)) {
            return "64";
        } else {
            return "32";
        }
    }
}
