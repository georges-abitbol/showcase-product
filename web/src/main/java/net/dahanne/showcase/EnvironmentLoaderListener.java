package net.dahanne.showcase;

import net.dahanne.showcase.ad.services.EventsService;
import net.dahanne.showcase.ad.services.impl.EventsServiceImpl;
import net.dahanne.showcase.persistence.DataAccessException;
import net.dahanne.showcase.persistence.JdbcUtilities;
import net.dahanne.showcase.persistence.services.AccountService;
import net.dahanne.showcase.persistence.services.UserService;
import net.dahanne.showcase.persistence.services.impl.AccountServiceImpl;
import net.dahanne.showcase.persistence.services.impl.UserServiceImpl;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.signature.QueryStringSigningStrategy;
import org.h2.jdbcx.JdbcDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by anthony on 2014-10-18.
 */
public class EnvironmentLoaderListener implements ServletContextListener {

  private static final Logger LOG = LoggerFactory.getLogger(EnvironmentLoaderListener.class);

  @Override
  public void contextInitialized(ServletContextEvent arg0) {

    LOG.debug("contextInitialized");

    String consumerKey = System.getProperty("consumerKey");
    if (consumerKey == null) {
      System.err.println("You have to provide the consumerKey, using -DconsumerKey");
      System.exit(3);
    }

    String consumerSecret = System.getProperty("consumerSecret");
    if (consumerSecret == null) {
      System.err.println("You have to provide the apiToken, using -DconsumerSecret");
      System.exit(3);
    }

    DataSource dataSource;
    String herokuDb = System.getProperty("herokuDb");
    if (herokuDb == null) {
      JdbcDataSource source = new JdbcDataSource();
      LOG.warn("Staring the app using embedded h2 db");
      source.setURL("jdbc:h2:./showcaseDatabase");
      source.setUser("sa");
      source.setPassword("sa");
      dataSource = source;
//      dataSource = new net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy(source);
    } else {
      try {
        URI dbUri = null;
        dbUri = new URI(System.getenv("DATABASE_URL"));
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String host = dbUri.getHost();
        int port = dbUri.getPort();
        String databaseName = dbUri.getPath().substring(1);

        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerName(host);
        source.setPortNumber(port);
        source.setDatabaseName(databaseName);
        source.setUser(username);
        source.setPassword(password);
        source.setLogLevel(2);
        dataSource = source;
      } catch (URISyntaxException e) {
        throw new RuntimeException(e);
      }
    }


    try {
      // To uncomment in case of redeploy to heroku
      if (herokuDb == null) {
        JdbcUtilities.cleanUpDB("./showcaseDatabase");
//        JdbcUtilities.dropTables(dataSource);
        JdbcUtilities.createTables(dataSource);
        JdbcUtilities.addSampleData(dataSource);
      }
    } catch (DataAccessException e) {
      throw new RuntimeException("There was an error initializing the database, the application can't continue....", e);
    }

    ServiceLocator locator = new ServiceLocator();
    UserService userServiceImpl = new UserServiceImpl(dataSource);
    AccountService accountServiceImpl = new AccountServiceImpl(dataSource);

    EventsService eventsServiceImpl = new EventsServiceImpl(accountServiceImpl, userServiceImpl);

    OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
    consumer.setSigningStrategy(new QueryStringSigningStrategy());

    locator.loadService(UserService.class, userServiceImpl);
    locator.loadService(AccountService.class, accountServiceImpl);
    locator.loadService(EventsService.class, eventsServiceImpl);

    locator.loadService(OAuthConsumer.class, consumer);

    ServiceLocator.load(locator);

  }

  @Override
  public void contextDestroyed(ServletContextEvent arg0) {
    // TODO Auto-generated method stub

  }

}
