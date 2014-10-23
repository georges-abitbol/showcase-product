Welcome to the Showcase Product !
=================================

It's a multi module maven project (well for now there's only the parent and a web module)

## To build and test the war archive :
$ mvn clean package


## Then, to test-drive locally :
```
$ mvn  jetty:run -Dorg.slf4j.simpleLogger.defaultLogLevel=debug -Dlog4jdbc.spylogdelegator.name=net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator -DconsumerKey=XXX -DconsumerSecret=XXX
```

## To deploy to Heroku :
```
$ heroku create
$ git push heroku master
```

in heroku dahsboard, go to settings and tweak JAVA_OPTS env. variable with -DconsumerKey=XXX -DconsumerSecret=XXX

## There's an example running on heroku at :
https://showcase-product.herokuapp.com/#/
Rest services for users : https://showcase-product.herokuapp.com/api/users
and accounts : https://showcase-product.herokuapp.com/api/accounts