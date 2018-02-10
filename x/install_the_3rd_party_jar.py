# encoding: utf-8
#!/usr/bin/python
import sys, os, string

def main( ) :
	os.system( 'echo "\n\n\n\n"' );	
	
	os.system( 'mvn install:install-file -DgroupId=uk.org.simonsite -DartifactId=log4j-rolling-appender -Dpackaging=jar -Dversion=20120703-0826 -Dfile=lib/log4j-rolling-appender-20120425-2221.jar -DgeneratePom=true;' );
	
	os.system( 'mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc -Dpackaging=jar -Dversion=6.0 -Dfile=lib/ojdbc-6.0.jar -DgeneratePom=true;' );
	
	#os.system( 'mvn install:install-file -DgroupId=com.danga -DartifactId=java-memcached -Dfile=lib/java-memcached-client-2.6.6.jar -Dversion=2.6.6 -Dpackaging=jar;' );
	
	sys.exit(0)

if __name__ == '__main__' :
    sys.exit(main())
