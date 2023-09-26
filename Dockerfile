FROM clojure:temurin-17-lein-alpine
ENV filepath /var/rinha/source.rinha.json
ENV LEIN_SILENT true
COPY . /usr/src/app
WORKDIR /usr/src/app
CMD lein run $filepath