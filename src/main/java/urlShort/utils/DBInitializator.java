package urlShort.utils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Created by admin on 12/5/2016.
 */

public class DBInitializator {
    private static EmbeddedDatabase dbInitializator;

    private DBInitializator() {
    }

    public static synchronized EmbeddedDatabase getDbInitializator() {
        if (dbInitializator == null) {
            EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
            dbInitializator = embeddedDatabaseBuilder
                    .setType(EmbeddedDatabaseType.H2)
                    .setBlockCommentEndDelimiter(";")
                    .addScript("create-table.sql")
                    .build();
        }
        return dbInitializator;
    }

    public static JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDbInitializator());
    }
}
