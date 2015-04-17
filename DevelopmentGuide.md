# Compile command line #

For those with a hurry, the steps to get the source compiling command line are quite simple:
  1. Get and install [Apache Maven](http://maven.apache.org/)
  1. [Checkout](http://code.google.com/p/metadata-aggregator/source/checkout) the source code
  1. Open a command line shell and into the checked out directory do:
```
    $ cd metadata-aggregator
    $ mvn clean package
```
> This compiles all sources command line and should work out of the box.

# Getting into the IDE #

The Metadata Aggregator was developed using the eclipse-based Spring source's _Integrated Development Environment_. Here the steps to get the Metadata Aggregator into STS.

  1. First, follow the steps above to get the source compiling command line.
  1. Get and install the Spring source Tool Suite (STS) [here](http://www.springsource.com/developer/sts)
  1. In STS use **Import** > **Existing maven projects**, and select the top folder
  1. STS cannot understand few generated folders that have been configured into the classpath; so one has to do it manually by selecting all projects, right-click and selecting **Maven** > **Update project configuration** and then same with **Maven** > **Update dependencies**
  1. Done. You should have the Metadata Aggregator compiled into STS.
  1. We recommend disabling all validations in STS.

# Few points on basic STS troubleshooting #

STS is a powerful IDE including support for Java, JSPX, javascript, AspectJ, Spring Roo, among others. Here you have a few usage tips:

  1. Ignore the javascript error reported on jquery.
  1. Sometimes, when using **auto build**, it just gets recompiling forever (in Linux). Disable **auto build** and build manually... at least for a while.
  1. Doing **Maven** > **Update project configuration**, **Maven** > **Update dependencies** then **Clean all projects** usually helps.
  1. Tomcat support in Eclipse is a pain. So, **clean server folder** in context menu usually helps so things get re-deployed to known state as they should.
  1. It is recommended to extend the Tomcat start up time out to at least 1.5 mins. (This is for your development machine, in a middle-end dedicated server the DMS starts in under 30 secs.)
  1. We believe most issues are created when one runs the command line maven build and then refresh STS. Unfortunately, doing a command line `mvn clean install` to get all the tests checked is a must for any experienced developer. Our work around, cleaning the project space as mentioned above usually helps.

# Running the Metadata Aggregator from STS #

Running the Metadata Aggregator inside STS with a single worker architecture requires the following steps:

  1. Grab and install [MySQL](http://www.mysql.com/).
  1. Install [Tomcat 6](http://tomcat.apache.org/download-60.cgi)
  1. In MySQL, create a database called and a user with all privileges over it.
  1. Configure two dms home directories, one for development, and one for integration testing. You can do that by copying the sydma-install > config > hudson directory into your user folder. A few properties in the three property files make reference to the dms home directory, so you should change them from "/var/lib/tomcat5/hudson/jobs/DC2F/workspace/sydma-install/config/" to your dms.config directory.
  1. The worker.properties has a dms.wn.localRootPath directory. Point that to an existing directory
  1. The dms.properties have properties for the database jdbc url. Change the url to sydma\_int or sydma depending on the environment (development or integration)
  1. In sydma-web > src > main > filters change filter-dev and filter-int to point to your respectives dms homes. You should now be able to run cucumber as a maven target
To run on development mode, you can run in from maven as mvn tomcat:run of from Spring STS. To run from STS you need to deploy both the web and the httptunnel modules. Make sure your run configuration has proper memory seetings (I use -Xmx1024m -XX:MaxPermSize=256m , but less than that should also work) and you set the dms home properties (-Ddms.config.home=/Users/diego/dms.home -Ddms.worker.profile=worker-external.xml)

# Integration testing #
We use cucumber for integration test. All the features are created in the features directory in the root of the sydma-web module.

Cucumber Testing starts up Tomcat with the web application, and runs the test cases in Selenium. It does require a fair bit of memory, so you may need to increase maven defaults with export MAVEN\_OPTS="-Xmx512m -XX:MaxPermSize=256m"

The first time you run cucumber, you need to install a few gems in your system. To do that, you run
```
$ mvn -DcukeArgs="--tags @passing" integration-test -Dcucumber.installGems=true -P=int
```
Subsequent runs can be run as
```
$ mvn -DcukeArgs="--tags @passing" integration-test -P=int
```
You can annotate the test cases with different tags, both the scenarios or full features like:
```
@passing
Scenario: Administrator gets redirected to admin home page
    Given I log in as "administrator"
    Then I should be on the administrator home page
```
or
```
@passing
Feature: Test advertising of datasets
...
```
We have a full set of integration tests that cover the whole application. The downside of this is that running the whole test suite can take a long time. If you want to run only the tests that affect the functionality you are currently working on, you can mark them as work in progress (wip)

To run the features the @wip scenarios only, you run
`mvn -DcukeArgs="--tags @wip" integration-test -P=int`

If we have several @wip in the feature set (as we have several developers working at the same time in different regions of the system), command line you can restrict which feature file to scan for scenarios using -Dcucumber.features, like
```
$ mvn -Dcucumber.features=/home/carlos/Devel/sydma/sydma-web/features/dataset_file_move.feature -DcukeArgs="--tags @wip" integration-test -P=int
```
Be careful to provide the full path to the file (the relative path didn't work running from within sydma). You can provide the argument in Eclipse as well.