ARG JDK_VER
FROM eclipse-temurin:${JDK_VER}-jdk-alpine

# distribution type: vanilla, paper, waterfall
ARG DIST
ENV DIST ${DIST}

# version: 1.12.2(vanilla, paper), 1.12(waterfall)
ARG VERSION
ENV VERSION ${VERSION}

# build: for forge, velocity
ARG BUILD
ENV BUILD ${BUILD}

# install packages
RUN apk add --no-cache \
    python3 \
    py3-pip  \
    curl \
    bash \
    libwebp

RUN pip3 install mcrcon --ignore-installed six

# download jar
WORKDIR /dist
COPY download.sh .
RUN bash ./download.sh ${DIST} ${VERSION} ${BUILD}

VOLUME ["/dist/server"]
WORKDIR /dist/server
EXPOSE 25565
