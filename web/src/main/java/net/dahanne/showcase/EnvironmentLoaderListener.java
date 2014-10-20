package net.dahanne.showcase;

import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by anthony on 2014-10-18.
 */
public class EnvironmentLoaderListener  implements ServletContextListener {

  private static final Logger LOG = LoggerFactory.getLogger(EnvironmentLoaderListener.class);

  @Override
  public void contextInitialized(ServletContextEvent arg0) {

    LOG.debug("contextInitialized");

    JdbcDataSource source = new JdbcDataSource();
    source.setURL("jdbc:h2:./showcaseDatabase");
    source.setUser("sa");
    source.setPassword("sa");
//    DataSource dataSource = new net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy(source);

    try {
      JdbcUtilities.cleanUpDB("./showcaseDatabase");
      JdbcUtilities.createTables(source);
      JdbcUtilities.addSampleData(source);
    } catch (DataAccessException e) {
      LOG.error("There was an error initializing the database, the application can't continue....",e);
    }

    ServiceLocator locator = new ServiceLocator();
    UserService userServiceImpl =  new UserServiceImpl(source);
    locator.loadService(UserService.class, userServiceImpl);
    ServiceLocator.load(locator);

  }

  @Override
  public void contextDestroyed(ServletContextEvent arg0) {
    // TODO Auto-generated method stub

  }

}
