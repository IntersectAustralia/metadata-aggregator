# Installing Application Requirements #

This installion guide assumes you are installing on a clean CentOS 6 server (or similar Linux distribution) and therefore includes the installion of every tool and package you will need to setup and run the application.

Firstly we will install the Oracle java jdk 1.6 package.

On your local machine download the oracle java jdk 1.6 linux bin file from the oracle website

http://www.oracle.com/technetwork/java/javase/downloads/jdk6downloads-1902814.html

Then ssh into the server you wish to deploy to with the following command:
```
ssh [username]@[server name]
```

and run:
```
$ sudo su - 
$ yum install openssh-clients
```

In a seperate bash tab, locally run:
```
$ scp [download location]/jdk-6u[version]-linux-x64-rpm.bin [username]@[server name you are deploying to]:/tmp/
```

Close the local tab. Then back in the ssh session run (replacing [[version](version.md)] with the version number that you installed):
```
$ cd /tmp/
$ chmod a+x jdk-6u[version]-linux-x64-rpm.bin
$ ./jdk-6u[version]-linux-x64-rpm.bin

$ alternatives --install /usr/bin/java java /usr/java/jdk1.6.0_[version]/jre/bin/java 20000
$ alternatives --install /usr/bin/javaws javaws /usr/java/jdk1.6.0_[version]/jre/bin/javaws 20000
$ alternatives --install /usr/bin/javac javac /usr/java/jdk1.6.0_[version]/bin/javac 20000
$ alternatives --install /usr/bin/jarsigner jarsigner /usr/java/jdk1.6.0_[version]/bin/jarsigner 20000
$ alternatives --install /usr/bin/jar jar /usr/java/jdk1.6.0_[version]/bin/jar 20000 
```


Now that Java is installed next we will setup MySQL
```
$ yum install mysql mysql-server
```

Set the MySQL service to start on boot
```
$ chkconfig --level 2345 mysqld on; service mysqld start
```

Start the MySQL service
```
$ service mysqld start
```

Log into MySQL
```
mysql -u root
```
Then press CTRL-C to exit the input.

Install Apache Tomcat 6
```
$ yum -y install tomcat6 tomcat6-webapps tomcat6-admin-webapps
```

You need to config Tomcat. These instructions assume that Tomcat installed in the default location of /usr/share/tomcat6

```
$ vi /etc/tomcat6/tomcat6.conf
```

Add the following lines to the config file below the existing JAVA\_OPTS (Note: To insert text in a vi window you must enter insert mode by pressing the i key. Once you've finished inserting you must press the esc key and enter :wq followed by the enter key to save your changes and exit the vi).

```
JAVA_OPTS="${JAVA_OPTS} -Ddms.config.home=/usr/share/tomcat6/mda-data/dms.home"
JAVA_OPTS="${JAVA_OPTS} -Dsolr.solr.home=/usr/share/tomcat6/mda-data/solr"
JAVA_OPTS="${JAVA_OPTS} -XX:MaxPermSize=256m -XX:+CMSClassUnloadingEnabled -Xmx1024m -Xms512m"
```

Now we are ready to download the source code for this application
```
$ yum install svn unzip
$ exit
$ cd ~
$ mkdir code
$ cd code
$ svn checkout http://metadata-aggregator.googlecode.com/svn/trunk/ metadata-aggregator-read-only
$ svn checkout http://ammrf-dms.googlecode.com/svn/trunk/sdms sdms-read-only
```

Install Apache Maven version 2.2.1
```
$ cd /tmp/
$ wget http://mirror.overthewire.com.au/pub/apache/maven/maven-2/2.2.1/binaries/apache-maven-2.2.1-bin.tar.gz
$ tar xzf apache-maven-2.2.1-bin.tar.gz -C /usr/local
$ cd /usr/local
$ ln -s apache-maven-2.2.1 maven 
$ vi /etc/profile.d/maven.sh
```
Write these lines into the vi input
```
export M2_HOME=/usr/local/maven
export PATH=${M2_HOME}/bin:${PATH}
```

Exit the editor by entering :wq and then check that mvn is installed correctly by entering
```
$ source /etc/profile.d/maven.sh
$ mvn -version
```

Now install Apache Solr:
```
$ sudo su -
$ cd /tmp/
$ wget http://archive.apache.org/dist/lucene/solr/3.1.0/apache-solr-3.1.0.tgz
$ tar xfz apache-solr-3.1.0.tgz

$ mkdir /usr/share/tomcat6/webapps/apache-solr
$ cd /usr/share/tomcat6/webapps/apache-solr
$ unzip /tmp/apache-solr-3.1.0/dist/apache-solr-3.1.0.war


$ mkdir -p /usr/share/tomcat6/mda-data/solr
$ mkdir -p /usr/share/tomcat6/mda-data/mount-point
$ cd /usr/share/tomcat6/mda-data/solr
$ mkdir conf
$ mkdir data
$ cp /home/[username]/code/metadata-aggregator-read-only/sydma-install/resource/solr_conf/* conf
$ chown -R tomcat /usr/share/tomcat6/mda-data/
$ chown -R tomcat /usr/share/tomcat6/webapps/apache-solr
```


Now install JOAI:
```
$ cd /tmp/
$ wget --no-check-certificate https://sourceforge.net/projects/dlsciences/files/jOAI%20-%20OAI%20Provider_Harvester/v3.1.1.3/joai_v3.1.1.3.zip
$ unzip joai_v3.1.1.3.zip
$ cd /usr/share/tomcat6/webapps
$ mkdir oai
$ cd oai
$ unzip /tmp/joai_v3.1.1.3/oai.war
$ chown -R tomcat /usr/share/tomcat6/webapps/oai
$ chkconfig tomcat6 on && service tomcat6 restart
```

If you would like to run our mock wasm service to simulate the logging in process you need to do the following:
```
$ cd /tmp/
$ wget http://dist.groovy.codehaus.org/distributions/groovy-binary-2.1.1.zip
$ unzip groovy-binary-2.1.1.zip
$ export GROOVY_HOME="/tmp/groovy-2.1.1/"
$ export PATH=$PATH:$GROOVY_HOME/bin
$ cd /home/[username]/code/metadata-aggregator-read-only/wasm-server/
```
Now execute the the scripts to start the mock wasm server (Note: when you execute these commands no output will be displayed on the screen, so as soon as you see the line "nohup: ignoring input and appending output to `nohup.out'" you can press ctrl-c and continue).
```
$ nohup groovy wasmserver.groovy &
$ nohup groovy webserver.groovy &
```

Configure the app for deployment (note: when running the mvn install you will see several exceptions. These are generated by our tests which expect to see them to ensure the sdms is functioning as intended. The build should still be successful.)
```
$ exit
$ cd ~/code/sdms-read-only/lib
$ ./install.sh
$ cd ..
$ mvn clean
$ mvn install

$ cd ../metadata-aggregator-read-only/lib
$ ./install.sh
$ cd ../sydma-install
```

Open the environment script and ensure the paths in there match those of your system (i.e. JAVA\_HOME) and change the REMOTE\_USER to equal the username which you used to ssh into the server you are deploying to.
```
$ vi config/local-dc2f/env.sh
```

Insert changes by first pressing the i key, then esc when finished and save your changes and exit by entering :wq.

Now run the installation script that will deploy the app. You will be prompted to create passwords for some mock users in the system, please remember what you enter at this point as they are the credentials you will enter later to access the application.
```
$ cd bin
$ ./qa-installer.sh -h local-dc2f -b
```

Once the deployment is complete you will need to change some variables in a few properties files to ensure to you can log in:
```
$ sudo su -
$ cd /usr/share/tomcat6/mda-data/dms.home/
$ vi wasm.properties
```
Change the wasm\_url from localhost:8080 to equal your server name with :8080 on the end. Also do the same for application.url in applicationType.properties: http://YourServerName:8080.
```
$ vi applicationType.properties
$ service tomcat6 restart
```

Switch off selinux (Note: this is temporary, if you server is switched off for any reason then you will need to re-run this command)
```
$ setenforce 0
```

And finally, configure the system's iptable to allow connections on port 8080 and 8888
```
$ vi /etc/sysconfig/iptables
```
Insert the following line
```
-A INPUT -m state --state NEW -m tcp -p tcp --dport 8080 -j ACCEPT
-A INPUT -m state --state NEW -m tcp -p tcp --dport 8888 -j ACCEPT
```

Then restart the iptables service
```
$ service iptables restart
```


You are now ready to use the system.

The url for the application will be: http://YourServerName:8080/sydma-web

If you did not set up the mock wasm server then you may log in using the external user log in link on the home page using the following username and password

u: admin p: password

If you did set up the wasm server then you may log in with any of the following usernames and the passwords for these will be whatever the entered when prompted while running the installation script:

ictintersect1
ictintersect2
ictintersect3
ictintersect4

