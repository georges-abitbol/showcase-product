package net.dahanne.showcase;

import net.dahanne.showcase.persistence.*;
import net.dahanne.showcase.persistence.services.AccountService;
import net.dahanne.showcase.persistence.services.UserService;
import net.dahanne.showcase.persistence.services.impl.AccountServiceImpl;
import net.dahanne.showcase.persistence.services.impl.UserServiceImpl;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.signature.QueryStringSigningStrategy;
import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

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

    JdbcDataSource source = new JdbcDataSource();
    source.setURL("jdbc:h2:./showcaseDatabase");
    source.setUser("sa");
    source.setPassword("sa");
    DataSource dataSource = new net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy(source);

    try {
      JdbcUtilities.cleanUpDB("./showcaseDatabase");
      JdbcUtilities.createTables(dataSource);
      JdbcUtilities.addSampleData(dataSource);
    } catch (DataAccessException e) {
      LOG.error("There was an error initializing the database, the application can't continue....", e);
    }

    ServiceLocator locator = new ServiceLocator();
    UserService userServiceImpl = new UserServiceImpl(source);
    AccountService accountServiceImpl = new AccountServiceImpl(source);


    OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
    consumer.setSigningStrategy(new QueryStringSigningStrategy());

    locator.loadService(UserService.class, userServiceImpl);
    locator.loadService(AccountService.class, accountServiceImpl);

    locator.loadService(OAuthConsumer.class, consumer);

    ServiceLocator.load(locator);

  }

  @Override
  public void contextDestroyed(ServletContextEvent arg0) {
    // TODO Auto-generated method stub

  }

}
