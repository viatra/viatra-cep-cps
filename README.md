# VIATRA-CEP CPS

A cyberphysical systems demonstrator for VIATRA CEP.

1. Creating environment for running the central unit

1.1 Installing Eclipse
 - Download Eclipse Mars for RCP and RAP Developers from: http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/mars/1/eclipse-rcp-mars-1-win32-x86_64.zip
 - Unzip it to any location and start with eclipse.exe.
 - Use "Help/Install new software..." to install necessary plugins: 
	- Xtext 2.9.0 Complete SDK from: http://download.eclipse.org/modeling/tmf/xtext/updates/composite/milestones/
	- EMF-IncQuery SDK 1.1.0 from: http://download.eclipse.org/incquery/updates/release
	- VIATRA 0.8.0 from: https://hudson.eclipse.org/viatra/job/viatra-maintenance-0.8/lastSuccessfulBuild/artifact/releng/org.eclipse.viatra.update/target/repository/
	- Gemini JPA 1.2.0.M1 from: http://download.eclipse.org/gemini/jpa/updates

1.2 Creating database
 - Install PostgreSQL 9.4 from: http://www.enterprisedb.com/products-services-training/pgdownload#windows
 - Use the default 5432 port
 - Create default postgres user with "postgres" as password
 - Open pgAdmin III and create a new database called "environmentcontroller"
 
1.3 Installing RabbitMQ
 - Download and install RabbitMq server 3.5.1 from: https://www.rabbitmq.com/releases/rabbitmq-server/v3.5.1/
 - The central unit uses the default guest/guest credentials. This can be used only on localhost.
 
2. Importing and running the central unit application

2.1 Importing projects
 - Use "File/Import..." to import projects.
	- Select "General/Existing projects into workspace..." an select the central unit src folder
	- Or select Projects from Git, if you have acces to the https://github.com/FTSRG/viatra-cep-cps repository

2.2. Creating targer runtime
 - Download necessary bundles:
	- RabbitMQ client 3.5.1 from: http://central.maven.org/maven2/com/rabbitmq/amqp-client/3.5.1/amqp-client-3.5.1.jar
	- PostgreSQL JDBC driver from: https://jdbc.postgresql.org/download/postgresql-9.4-1202.jdbc41.jar
 - Add downloaded bundles to runtime with Window/Preferences/Plug-in developement/Target Platform
	- Select active platform and click edit
	- Add directory location and browse downloaded jars
 - Add JAX-RS Connector online repository to active platform
	- Add software site location
	- Use http://hstaudacher.github.io/osgi-jax-rs-connector
	- Select:
		- OSGi - JAX-RS Connector
		- Gson Provider
		- Sse Provider
	- Untick "Include required software"
 - Click Apply and OK
	
2.3. Creating run configuration.
 - Click on Run/Run configurations...
 - Select OSGi Framework and click on the New run configuration button
 - Select all the workspace bundles except web (it is a work in progress)
 - Set workspace bundle Start Level to 5 for all except data and dal
 - Unselect all Target platform bundles
 - Untick "Include optional dependencies..."
 - Add the following bundles:
	org.eclipse.gemini.jpa
	org.postgresql.jdbc41
	org.eclipse.equinox.console
	org.apache.felix.gogo.shell
	org.apache.felix.gogo.runtime
	com.eclipsesource.jaxrs.publisher
	com.eclipsesource.jaxrs.provider.gson
	com.eclipsesource.jaxrs.provider.sse
	org.eclipse.equinox.http.jetty
	org.eclipse.jetty.continuation
 - Click Add Required bundles
 - Switch "com.eclipsesource.jaxrs.jersey-min" to "com.eclipsesource.jaxrs.jersey-all"
 - Add "-Dorg.osgi.service.http.port=9090" to the VM arguments
 - Click Apply then Run
 

3. Using the simulation based test adapter
 - Import the hu.karaszi.ec.testadapter project
 - Add the bundle to the run configuration with start level 6
 - Start the simulation with typing "adapter:start" into the console and press enter

4. Using the Raspberry Pi based adapter
 - Get the Grade integration for Eclipse plugin from the Eclipse Marketplace
 - Import the hu.karaszi.ec.piadapter project
 - Right click on the project and select Gradle/Refresh all
 - Build the executable jar with Run as/Gradle build... and use the "jar" task
 - Start the jar on a Raspberry Pi with the following command: java -jar hu.karaszi.ec.piadapter
This requires an MCP3008 ADC connected to the SPI pins, and a sensor on the 1st analog channel. The SPI communication must be enabled before usage, see: https://www.raspberrypi.org/documentation/hardware/raspberrypi/spi/README.md
 

5. Troubleshooting
 - If there are exceptions form the declarative services at startup try restarting eclipse and cleaning the workspace with "Project/Clean..."
 - The Gemini JPA throws a few database exeptions at startup, these can be ignored. The central unit starts correctly, if the "Tick" events start to show in the console