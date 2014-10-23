package net.dahanne.showcase.persistence;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class JdbcUtilities {

  private static final Logger LOG = LoggerFactory.getLogger(JdbcUtilities.class);

  private JdbcUtilities() {
    // not instantiable
  }

  public static void cleanUpDB(String dbFileName) throws DataAccessException {
    delete(new File(dbFileName + ".mv.db"));
    delete(new File(dbFileName + ".trace.db"));

  }

  public static void createTables(DataSource dataSource) throws DataAccessException {
    List<String> sqlStatements = loadSqlStatements("tables_create.sql");
    executeSqlStatements(dataSource, sqlStatements);
  }

  public static void dropTables(DataSource dataSource) throws DataAccessException {
    List<String> sqlStatements = loadSqlStatements("tables_drop.sql");
    executeSqlStatements(dataSource, sqlStatements);
  }

  public static void addSampleData(DataSource dataSource) throws DataAccessException {
    List<String> sqlStatements = loadSqlStatements("add_sample_data.sql");
    executeSqlStatements(dataSource, sqlStatements);
  }

  private static void executeSqlStatements(DataSource dataSource, List<String> statements) throws DataAccessException {
    try {
      Connection conn = dataSource.getConnection();
      try {
        Statement statement = conn.createStatement();
        for (String command : statements) {
          statement.addBatch(command);
        }
        statement.executeBatch();
      } catch (SQLException e) {
        throw new DataAccessException(e);
      } finally {
        conn.close();
      }
    } catch (SQLException e) {
      throw new DataAccessException(e);
    }
  }

  private static List<String> loadSqlStatements(String name) throws DataAccessException {
    List<String> setup = new ArrayList<>();
    String setupScript;
    try {
      InputStream setupStream = JdbcUtilities.class.getClassLoader().getResourceAsStream(name);
      try {
        BufferedReader r = new BufferedReader(new InputStreamReader(setupStream, "US-ASCII"));
        StringBuilder sb = new StringBuilder();
        while (true) {
          String line = r.readLine();
          if (line == null) {
            break;
          } else if (!line.startsWith("--")) {
            sb.append(line).append("\n");
          }
        }
        setupScript = sb.toString();
      } finally {
        setupStream.close();
      }
    } catch (IOException e) {
      throw new DataAccessException(e);
    }

    for (String command : setupScript.split(";")) {
      if (!command.matches("\\s*")) {
        setup.add(command);
      }
    }
    return setup;
  }

  private static void delete(File f) {
    if (f.isDirectory()) {
      for (File c : f.listFiles())
        delete(c);
    }
    if (!f.delete())
      LOG.debug("Failed to delete file: " + f + " certainly because it does not exist yet");
  }

}
