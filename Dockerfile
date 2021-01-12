FROM openliberty/open-liberty:kernel-java8-openj9-ubi

ARG VERSION=1.0
ARG REVISION=SNAPSHOT

ARG ARTIFACT_NAME=backend4frontend

LABEL \
  org.opencontainers.image.authors="Marvin Kienitz" \
  org.opencontainers.image.vendor="Open Liberty" \
  org.opencontainers.image.url="local" \
  org.opencontainers.image.source="https://github.com/JapuDCret/ba-demo-backend4frontend" \
  org.opencontainers.image.version="$VERSION" \
  org.opencontainers.image.revision="$REVISION" \
  vendor="Open Liberty" \
  name="$ARTIFACT_NAME" \
  version="$VERSION-$REVISION" \
  summary="The $ARTIFACT_NAME microservice" \
  description="This image contains the $ARTIFACT_NAME microservice running with the Open Liberty runtime."

COPY --chown=1001:0 \
    src/main/liberty/config \
    /config/

# change directory from which server.xml is copied
COPY --chown=1001:0 \
    target/liberty/wlp/usr/servers/$ARTIFACT_NAME/configDropins/overrides/ \
    /config/configDropins/overrides/

COPY --chown=1001:0 \
    target/$ARTIFACT_NAME.war \
    /config/apps

RUN configure.sh