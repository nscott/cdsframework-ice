# Knowledge repository is ran as a separate build stage since it usually succeeds, and it's much faster/simpler to grab deps.
# No need to repeat it multiple times.
# If you're having trouble with any of these steps, especially if you're on OSX using Docker Desktop,
# make sure you've allocated enough RAM and disk space by checking your docker daemon.
# You can do this through the docker desktop app under "Resources" in the configuration panel.
# At least 8gb of RAM is recommened, and ~5gb of disk.
FROM --platform=linux/amd64 azul/zulu-openjdk-alpine:8 AS dependency-cache-opencds-knowledge-repository
RUN apk --no-cache -U upgrade --available && apk --no-cache add maven openssl --upgrade
ENV HOME=/usr/app
RUN mkdir -p ${HOME}
WORKDIR ${HOME}
# Maven may choke if it can't consume outrageous amounts of memory.
ENV MAVEN_OPTS="-Xmx16G"

ADD opencds/opencds-knowledge-repository-data/pom.xml ${HOME}/opencds/opencds-knowledge-repository-data/pom.xml 
RUN cd opencds/opencds-knowledge-repository-data && mvn clean
RUN cd opencds/opencds-knowledge-repository-data && mvn dependency:go-offline


# --------------------------------------------------------------------------------------------------------
# The maven dependencies take *forever* to download and they often time out. This stage aims to remedy that.
# Copy just the POM files, then ask Maven to download everything. Docker will cache the steps for us.
FROM --platform=linux/amd64 azul/zulu-openjdk-alpine:8 AS dependency-cache
RUN apk --no-cache -U upgrade --available && apk --no-cache add maven openssl --upgrade
ENV HOME=/usr/app
RUN mkdir -p ${HOME}
WORKDIR ${HOME}
COPY --from=dependency-cache-opencds-knowledge-repository /root/.m2 /root/.m2
# Maven may choke if it can't consume outrageous amounts of memory.
ENV MAVEN_OPTS="-Xmx16G"

# Add only the POMs. If you ADD the entire source tree, it will trigger re-builds of this stage
# any time the source changes. Adding only POMs will trigger rebuilds only when those files change.

ADD cdsframework-support-xml/pom.xml ${HOME}/cdsframework-support-xml/pom.xml

ADD ice3/opencds-ice-service/pom.xml ${HOME}/ice3/opencds-ice-service/pom.xml 

# Even though we already cached this in the first stage, it's part of the build dep chain, so include the pom here.
ADD opencds/opencds-knowledge-repository-data/pom.xml ${HOME}/opencds/opencds-knowledge-repository-data/pom.xml 

ADD opencds/opencds-parent/pom.xml ${HOME}/opencds/opencds-parent/pom.xml

ADD opencds/opencds-parent/dss-java-stub/pom.xml ${HOME}/opencds/opencds-parent/dss-java-stub/pom.xml

ADD opencds/opencds-parent/opencds-common/pom.xml ${HOME}/opencds/opencds-parent/opencds-common/pom.xml

ADD opencds/opencds-parent/opencds-config/pom.xml ${HOME}/opencds/opencds-parent/opencds-config/pom.xml
ADD opencds/opencds-parent/opencds-config/opencds-config-api/pom.xml ${HOME}/opencds/opencds-parent/opencds-config/opencds-config-api/pom.xml
ADD opencds/opencds-parent/opencds-config/opencds-config-cli/pom.xml ${HOME}/opencds/opencds-parent/opencds-config/opencds-config-cli/pom.xml
ADD opencds/opencds-parent/opencds-config/opencds-config-client/pom.xml ${HOME}/opencds/opencds-parent/opencds-config/opencds-config-client/pom.xml
ADD opencds/opencds-parent/opencds-config/opencds-config-file/pom.xml ${HOME}/opencds/opencds-parent/opencds-config/opencds-config-file/pom.xml
ADD opencds/opencds-parent/opencds-config/opencds-config-mappers/pom.xml ${HOME}/opencds/opencds-parent/opencds-config/opencds-config-mappers/pom.xml
ADD opencds/opencds-parent/opencds-config/opencds-config-migrate/pom.xml ${HOME}/opencds/opencds-parent/opencds-config/opencds-config-migrate/pom.xml
ADD opencds/opencds-parent/opencds-config/opencds-config-rest/pom.xml ${HOME}/opencds/opencds-parent/opencds-config/opencds-config-rest/pom.xml
ADD opencds/opencds-parent/opencds-config/opencds-config-schema/pom.xml ${HOME}/opencds/opencds-parent/opencds-config/opencds-config-schema/pom.xml
ADD opencds/opencds-parent/opencds-config/opencds-config-service/pom.xml ${HOME}/opencds/opencds-parent/opencds-config/opencds-config-service/pom.xml
ADD opencds/opencds-parent/opencds-config/opencds-config-store/pom.xml ${HOME}/opencds/opencds-parent/opencds-config/opencds-config-store/pom.xml

ADD opencds/opencds-parent/opencds-decision-support-service/pom.xml ${HOME}/opencds/opencds-parent/opencds-decision-support-service/pom.xml

ADD opencds/opencds-parent/opencds-dss-components/pom.xml ${HOME}/opencds/opencds-parent/opencds-dss-components/pom.xml
ADD opencds/opencds-parent/opencds-dss-components/opencds-dss-drools-55-adapter/pom.xml ${HOME}/opencds/opencds-parent/opencds-dss-components/opencds-dss-drools-55-adapter/pom.xml
ADD opencds/opencds-parent/opencds-dss-components/opencds-dss-drools-61-adapter/pom.xml ${HOME}/opencds/opencds-parent/opencds-dss-components/opencds-dss-drools-61-adapter/pom.xml
ADD opencds/opencds-parent/opencds-dss-components/opencds-dss-evaluation/pom.xml ${HOME}/opencds/opencds-parent/opencds-dss-components/opencds-dss-evaluation/pom.xml
ADD opencds/opencds-parent/opencds-dss-components/opencds-dss-metadata-discovery/pom.xml ${HOME}/opencds/opencds-parent/opencds-dss-components/opencds-dss-metadata-discovery/pom.xml
ADD opencds/opencds-parent/opencds-dss-components/opencds-dss-query/pom.xml ${HOME}/opencds/opencds-parent/opencds-dss-components/opencds-dss-query/pom.xml
ADD opencds/opencds-parent/opencds-dss-components/opencds-dss-sample-java-engine/pom.xml ${HOME}/opencds/opencds-parent/opencds-dss-components/opencds-dss-sample-java-engine/pom.xml

ADD opencds/opencds-parent/opencds-fhir-evaluation/pom.xml ${HOME}/opencds/opencds-parent/opencds-fhir-evaluation/pom.xml

ADD opencds/opencds-parent/opencds-guvnor-support/pom.xml ${HOME}/opencds/opencds-parent/opencds-guvnor-support/pom.xml

ADD opencds/opencds-parent/opencds-plugins/pom.xml ${HOME}/opencds/opencds-parent/opencds-plugins/pom.xml
ADD opencds/opencds-parent/opencds-plugins/opencds-plugin-api/pom.xml ${HOME}/opencds/opencds-parent/opencds-plugins/opencds-plugin-api/pom.xml
ADD opencds/opencds-parent/opencds-plugins/opencds-plugin-debug/pom.xml ${HOME}/opencds/opencds-parent/opencds-plugins/opencds-plugin-debug/pom.xml
ADD opencds/opencds-parent/opencds-plugins/opencds-plugin-encounters/pom.xml ${HOME}/opencds/opencds-parent/opencds-plugins/opencds-plugin-encounters/pom.xml
ADD opencds/opencds-parent/opencds-plugins/opencds-plugin-support/pom.xml ${HOME}/opencds/opencds-parent/opencds-plugins/opencds-plugin-support/pom.xml

ADD opencds/opencds-parent/opencds-terminology-support/pom.xml ${HOME}/opencds/opencds-parent/opencds-plugins/opencds-terminology-support/pom.xml
ADD opencds/opencds-parent/opencds-terminology-support/opencds-apelon/pom.xml ${HOME}/opencds/opencds-parent/opencds-plugins/opencds-terminology-support/opencds-apelon/pom.xml

ADD opencds/opencds-parent/opencds-vmr-1_0/pom.xml ${HOME}/opencds/opencds-parent/opencds-vmr-1_0/pom.xml
ADD opencds/opencds-parent/opencds-vmr-1_0/opencds-vmr-1_0-internal/pom.xml ${HOME}/opencds/opencds-parent/opencds-vmr-1_0/opencds-vmr-1_0-internal/pom.xml
ADD opencds/opencds-parent/opencds-vmr-1_0/opencds-vmr-1_0-mappings/pom.xml ${HOME}/opencds/opencds-parent/opencds-vmr-1_0/opencds-vmr-1_0-mappings/pom.xml
ADD opencds/opencds-parent/opencds-vmr-1_0/opencds-vmr-1_0-schema/pom.xml ${HOME}/opencds/opencds-parent/opencds-vmr-1_0/opencds-vmr-1_0-schema/pom.xml

ADD opencds/opencds-parent/opencds-vmr-evaluation/pom.xml ${HOME}/opencds/opencds-parent/opencds-vmr-evaluation/pom.xml

ADD opencds-rest-service/pom.xml ${HOME}/opencds-rest-service/pom.xml

ADD opencds-support-client/pom.xml ${HOME}/opencds-support-client/pom.xml

ADD rules-packager/pom.xml ${HOME}/rules-packager/pom.xml

# We separate the clean and copy-deps steps to allow mvn clean to be cached for multiple runs.
# --fail-never is used since certain dependencies WILL fail, since they aren't yet built. That's okay.
# We just want to resolve as much as we can.
RUN cd opencds/opencds-parent && mvn clean
RUN cd opencds/opencds-parent && mvn -DskipTests -DskipITs --fail-never dependency:go-offline


# --------------------------------------------------------------------------------------------------------
# Ideally eclipse-temurin:8-jdk-alpine would be used, but it doesn't support ciphers
# we need to download some POM dependencies. See https://github.com/adoptium/temurin-build/issues/3002
FROM --platform=linux/amd64 azul/zulu-openjdk-alpine:8 AS compile

RUN apk --no-cache -U upgrade --available && apk --no-cache add maven openssl --upgrade

ENV BUILD_DIR=/var/opt/opencds-build
RUN mkdir -p ${BUILD_DIR}
WORKDIR ${BUILD_DIR}
COPY --from=dependency-cache /root/.m2 /root/.m2

ENV JAVA_HOME=/usr/lib/jvm/default-jvm
ENV PATH="$JAVA_HOME/bin:${PATH}"
# Maven may choke if it can't consume outrageous amounts of memory.
ENV MAVEN_OPTS="-Xmx16G"

COPY ./opencds opencds
COPY ./opencds-support-client opencds-support-client
COPY ./cdsframework-support-xml cdsframework-support-xml
COPY ./ice3 ice3
COPY ./opencds-rest-service opencds-rest-service
COPY ./rules-packager rules-packager
COPY ./LICENSE-header.txt ./LICENSE-header.txt
# opencds-ice-service fails without a git directory which is weird
COPY ./.git .git

# While we downloaded *most* of the dependencies, dependency:go-offline doesn't actually do everything we may need.
# Go figure. We can't use offline mode (-o) since there are some plugins and things that are out-of-band from the
# dependency:go-offline command. Allow things to install naturally here. Still way after than tightly coupling
# MOST of the dependency downloads to this build step.
RUN cd opencds/opencds-knowledge-repository-data && mvn install
RUN cd opencds/opencds-parent && mvn -e -DskipTests -DskipITs install

# Forces the container to stay up for debugging usage
ENTRYPOINT ["tail", "-f", "/dev/null"]


# --------------------------------------------------------------------------------------------------------
FROM --platform=linux/amd64 tomcat:9-jre8-temurin-jammy AS build

# Metadata
LABEL organization="HLN Consulting, LLC"
LABEL maintainer="Sam Nicolary<sdn@hln.com>"

ENV DEBUG="N"
ENV KM_THREADS="8"
ENV OUTPUT_EARLIEST_OVERDUE_DATES="Y"
ENV ENABLE_DOSE_OVERRIDE_FEATURE="Y"
ENV OUTPUT_SUPPLEMENTAL_TEXT="Y"
ENV REMOTE_CONFIG_ENABLED="N"
ENV REMOTE_CONFIG_USER="remote_admin"
ENV REMOTE_CONFIG_PASSWORD="password"
ENV MAVEN_OPTS="-Xmx8G"
ENV JAVA_OPTS="-Xmx8G"

# Tomcat Server
EXPOSE 8080

RUN adduser -u 1000 --home /home/appuser appuser && \
            mkdir -p /home/appuser/.opencds && \
            rm -rf /usr/local/tomcat/webapps/docs /usr/local/tomcat/webapps/ROOT /usr/local/tomcat/webapps/examples /usr/local/tomcat/webapps/host-manager /usr/local/tomcat/webapps/manager

WORKDIR /home/appuser

RUN mkdir -p /usr/local/projects/ice/ice3/opencds-ice-service/src/main/resources

# copy config files
COPY --from=compile /var/opt/opencds-build/opencds/opencds-parent/opencds-decision-support-service/target/opencds-decision-support-service-2.0.5 \
        /usr/local/tomcat/webapps/opencds-decision-support-service
COPY --from=compile /var/opt/opencds-build/ice3 /usr/local/projects/ice/
COPY --from=compile /var/opt/opencds-build/ice3/opencds-ice-service/src/main/resources \
        /usr/local/tomcat/webapps/opencds-decision-support-service/opencds-ice-service/src/main/resources
COPY --from=compile /var/opt/opencds-build/ice3/opencds-ice-service /usr/local/projects/ice/

COPY .gcp.config/opencds.properties .opencds
COPY .gcp.config/sec.xml .opencds
COPY .gcp.config/log4j2.xml /usr/local/tomcat/webapps/opencds-decision-support-service/WEB-INF/classes
COPY .gcp.config/ice.properties /usr/local/tomcat/webapps/opencds-decision-support-service/WEB-INF/classes
COPY .gcp.config/start-ice.sh ./
COPY .gcp.config/server.xml /usr/local/tomcat/conf

RUN chown -R appuser: /home/appuser /usr/local/tomcat

USER appuser

# Run Tomcat server
CMD ["./start-ice.sh", "catalina.sh", "run"]
