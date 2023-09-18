FROM clojure:temurin-17-lein-alpine
ENV file combination
COPY . /usr/src/app
WORKDIR /usr/src/app
CMD lein run $file