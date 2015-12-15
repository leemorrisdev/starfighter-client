package uk.co.leemorris.starfighter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author lmorris
 */
public class TestUtil {

    private TestUtil() {}

    public static String getJSON(String fileName) throws Exception {

        Path path = Paths.get(TestUtil.class.getResource("/sample-responses/" + fileName).toURI());

        return new String(Files.readAllBytes(path)).replace("\r", "").replace("\n", "");
    }
}
