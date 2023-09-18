# rinha-de-compiler-clj

## Como usar

Todos os arquivos de teste devem estar na pasta resources, para gerar mais, é necessário criar a partir do 
[repositório oficial da rinha de compiladores](https://github.com/aripiprazole/rinha-de-compiler/tree/main).

### Leiningen
```
lein run nome_do_arquivo
```

### Docker
```
docker build -t rinha-clj .
docker run -e file=nome_do_arquivo rinha-clj
```
