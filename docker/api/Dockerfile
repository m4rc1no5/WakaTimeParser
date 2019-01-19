FROM m4rc1no5/wildfly:15.0.1.Final
MAINTAINER marcin.zaremba@gmail.com

RUN $JBOSS_HOME/bin/add-user.sh m4rc1no5 m4rc1no5 --silent

# deploy
COPY WakaTimeParser-api.war $JBOSS_HOME/standalone/deployments/