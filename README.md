# rinha-de-compiler-clj

## Como usar

Todos os arquivos de teste devem estar na pasta resources, para gerar mais, é necessário criar a partir do 
[repositório oficial da rinha de compiladores](https://github.com/aripiprazole/rinha-de-compiler/tree/main).

### Leiningen
```
lein run path_do_arquivo
lein run resources/files/combination.json
lein run /var/rinha/source.rinha.json
```

### Docker
```
docker build -t rinha-clj .
docker run -e filepath=path_do_arquivo rinha-clj
docker run -v /var/rinha/source.rinha.json:/var/rinha/source.rinha.json rinha-clj
docker run -e filepath=resources/files/combination.json rinha-clj
```
