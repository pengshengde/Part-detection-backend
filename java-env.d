# /etc/profile: system-wide .profile file for the Bourne shell (sh(1))
# and Bourne compatible shells (bash(1), ksh(1), ash(1), ...).

if [ "$(id -u)" -eq 0 ]; then
  PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"
else
  PATH="/usr/local/bin:/usr/bin:/bin:/usr/local/games:/usr/games"
fi
export PATH

# JAVA
JAVA_HOME=/usr/local/java/jdk-17.0.8
CLASSPATH=$JAVA_HOME/lib/
PATH=$PATH:$JAVA_HOME/bin
export PATH JAVA_HOME CLASSPATH

export MAVEN_HOME=/usr/local/maven/apache-maven-3.9.4
export PATH=$MAVEN_HOME/bin:$PATH

#MYSQL
export PATH=$PATH:/home/mysql/mysql3306/bin
