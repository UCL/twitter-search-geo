# twitter-search-geo

![Java CI with Maven](https://github.com/UCL/twitter-search-geo/workflows/Java%20CI%20with%20Maven/badge.svg)

## Background

## Features

## Technologies

- Jakarta EE
- JPA
- JAX-RS
- REST


## Installation

### Requirements

- Java 17
- Glassfish 7

### Configuration

#### Database connection pool

Configure Glassfish for access to the database

Create a connection pool:

```
asadmin create-jdbc-connection-pool \
    --datasourceclassname org.postgresql.ds.PGConnectionPoolDataSource \
    --restype javax.sql.ConnectionPoolDataSource \
    --property password=<pass>:databaseName=<dbname>:serverName=<hostname>:user=testdb TwitterSearchGeoPool
```

Create a JDBC resource:

```
asadmin create-jdbc-resource --connectionpoolid TwitterSearchGeoPool jdbc/TwitterSearchGeoPool
```

#### TLS: Root CA certificate

Download the root CA certificate for Twitter API from https://cacerts.digicert.com/DigiCertGlobalRootCA.crt.pem

Import the certificate to the CA certs keystore in Glassfish

```
keytool -import -alias digicertglobal-root-ca \
        -file DigiCertGlobalRootCA.crt.pem \
        -keystore /opt/glassfish7/glassfish/domains/domain1/config/cacerts.jks
```

#### API secrets

Add `app1.key` and `app1.secret` password aliases to Glassfish

```
asadmin create-password-alias
```

Register these password aliases under a the Java system properties `APP1-KEY` and `APP1-SECRET`

```
asadmin create-system-properties APP1-KEY='${ALIAS\=app1.key}'
asadmin create-system-properties APP1-SECRET='${ALIAS\=app1.secret}'
```

### Deployment

Deploy the EJB application to Glassfish

```
asadmin deploy --name <applicationName> <path-to>/twitter-search-geo-ejb.jar
```

## Building

Build package with Maven

```
mvn package
```

## Reporting bugs

Please use the GitHub issue tracker for any bugs or feature suggestions:

[https://github.com/UCL/twitter-search-geo/issues](https://github.com/UCL/twitter-search-geo/issues)

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate. Analyse your code with [CheckStyle][checkstyle] (Sun Checks) and [Findbugs/Spotbugs][findbugs].

Please keep the use of 3rd party runtime libraries to a minimum. The application is expected to run on a standard Java EE 8 server ([Eclipse Glassfish 5 full profile][eclipse-glassfish-5]).

## Authors

- David Guzman (Github: [@david-guzman](https://github.com/david-guzman))

## Copyright


&copy; 2019 UCL ([https://www.ucl.ac.uk](https://www.ucl.ac.uk)).

[eclipse-glassfish-5]: https://projects.eclipse.org/projects/ee4j.glassfish/downloads
[checkstyle]: https://checkstyle.org/
[findbugs]: https://github.com/findbugsproject/findbugs
[javaee-glassfish-5]: https://javaee.github.io/glassfish/
[twitter-app-dashboard]: https://developer.twitter.com/en/apps
