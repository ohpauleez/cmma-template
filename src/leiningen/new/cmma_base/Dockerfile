FROM java:8
MAINTAINER Your Name <you@example.com>

ADD target/{{name}}-0.1.0-SNAPSHOT-standalone.jar /{{name}}/app.jar

EXPOSE 8080

CMD ["java", "-cp", "/{{name}}/app.jar", "clojure.main", "-m", "{{namespace}}"]
