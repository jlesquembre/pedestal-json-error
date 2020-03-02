Start a server with `clj -A:run`, and make some requests:

```
curl localhost:8888 -H "Content-Type: application/json" -d 'test'
```

```
curl localhost:8888 -H "Content-Type: application/json" -d '["test"'
```
