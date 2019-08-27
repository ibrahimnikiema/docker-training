FROM ivyrepo.mutualofomaha.com:5003/com.mutualofomaha.img/openjdk:10.0-jre
RUN mkdir /data
WORKDIR /app
ADD ./build/distributions/groovy-sample-app-1.0-SNAPSHOT.tar /app
ENTRYPOINT ["/app/groovy-sample-app-1.0-SNAPSHOT/bin/groovy-sample-app"]