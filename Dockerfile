FROM clojure:temurin-17-lein-alpine
ENV filepath /var/rinha/source.rinha.json
COPY . /usr/src/app
WORKDIR /usr/src/app
CMD lein run $filepath