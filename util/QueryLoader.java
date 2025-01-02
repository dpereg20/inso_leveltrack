package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class QueryLoader {
    private static final Properties queries = new Properties();

    static {
        try (InputStream input = QueryLoader.class.getClassLoader().getResourceAsStream("queries.properties")) {
            if (input == null) {
                throw new IOException("Archivo queries.properties no encontrado en el classpath.");
            }
            queries.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getQuery(String key) {
        return queries.getProperty(key);
    }
}

