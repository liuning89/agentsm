# AGENTS.md

## Cursor Cloud specific instructions

### Project overview

This is **agentsm** (Agent Service Management) — a legacy Java/Maven WAR application for managing real estate agents, built with Spring MVC 4.0.3 + MyBatis + JSP. It targets Java 1.7 source/target.

### Prerequisites (installed in update script)

- **Java 8** (OpenJDK 8) — required for Java 1.7 source/target compatibility
- **Maven 3.8+** — build tool
- **MySQL 8.0** — three schemas: `miles`, `report`, `log`
- **ZooKeeper** — required for Dubbo service registry (app blocks on startup without it)

### Internal dependency stubs

The project depends on ~15 proprietary `com.lifang.*` JAR artifacts hosted on an unreachable internal Nexus (`10.0.18.175`). Stub JARs are generated from source in `operationmgmt_sys/stubs/` and installed to the local Maven repo. If compilation fails on missing classes, check and update stubs.

To rebuild and reinstall stubs:
```bash
cd /workspace/operationmgmt_sys/stubs
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
CLASSPATH=$(find ~/.m2/repository -name "*.jar" -not -path "*/com/lifang/*" -not -path "*/com/leo/*" | tr '\n' ':')
find src -name "*.java" > sources.txt
rm -rf classes && mkdir -p classes
javac -source 1.7 -target 1.7 -cp "$CLASSPATH" -d classes @sources.txt
jar cf stubs.jar -C classes .
# Then reinstall for each groupId:artifactId:version (see the DEPS array in the update script)
```

### Build commands

```bash
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
cd /workspace/operationmgmt_sys
mvn compile -P local          # compile only
mvn package -P local -DskipTests  # build WAR
```

### Running the application

```bash
# Start MySQL and ZooKeeper first
sudo service mysql start
CLASSPATH="/etc/zookeeper/conf:/usr/share/java/zookeeper.jar:/usr/share/java/zookeeper-jute.jar" \
  /usr/lib/jvm/java-21-openjdk-amd64/bin/java org.apache.zookeeper.server.ZooKeeperServerMain /etc/zookeeper/conf/zoo.cfg &

# Then run Jetty
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
cd /workspace/operationmgmt_sys
mvn jetty:run -P local
```

The app starts on **http://localhost:8080**. Login page: `/login.action`.

### Key gotchas

- **Both `local` and `dev` profiles are activeByDefault** in `pom.xml`. Using `-P local` explicitly activates only the `local` profile.
- **`conf/local/` is gitignored** — local config files (jdbc, redis, memcached, sso, dubbo) must be created manually pointing to localhost services.
- **ZooKeeper must be running** before Jetty starts. Without it, Dubbo's ZkClient will retry connections indefinitely, blocking application startup.
- **No automated tests exist** — there is no `src/test/` directory and no test files in the project.
- **SSO is stubbed** — the `SsoFilter` is a pass-through stub; real SSO authentication to `devyun.wkzf.com` is not available.
- **Redis/Memcached not required for basic startup** — the `JedisClusterBuilder.build()` stub returns null, and the `MemcachedSessionFilter` stub is a pass-through.
- **`spring-sso.xml`** was added to `src/main/resources/spring/` as it's expected by `web.xml` but was originally packaged inside the (unavailable) `sso-client` JAR.
- The `log4j:ERROR catAppender` warnings at startup are harmless — the Dianping CAT monitoring appender is not available.
