# Реализовать сервис заказа. Сервис биллинга. Сервис нотификаций.

1. Клонировать репозиторий проекта

```
git clone git@github.com:themuvarov/homework7.git
```

2. Установить Temporal.io, Kafka, Postgresql

```
git clone https://github.com/temporalio/helm-charts.git
cd helm-charts
helm install     --set server.replicaCount=1     --set cassandra.config.cluster_size=1     --set prometheus.enabled=false     --set grafana.enabled=false     --set elasticsearch.enabled=false     temporaltest . --timeout 15m
helm install pg-billing --set global.postgresql.auth.username=billing --set global.postgresql.auth.password=billing --set global.postgresql.auth.database=billing  oci://registry-1.docker.io/bitnamicharts/postgresql
helm install pg-notify --set global.postgresql.auth.username=notify --set global.postgresql.auth.password=notify --set global.postgresql.auth.database=notify  oci://registry-1.docker.io/bitnamicharts/postgresql
helm install pg-bike --set global.postgresql.auth.username=bike --set global.postgresql.auth.password=bike --set global.postgresql.auth.database=bike  oci://registry-1.docker.io/bitnamicharts/postgresql
helm install kafka --set sasl.enabledMechanisms=PLAIN --set sasl.client.users={"user1"} --set sasl.client.passwords="1234567" oci://registry-1.docker.io/bitnamicharts/kafka

  ```
3. Дождаться запуска pod temporal.io
4. Создать namespace default
```
kubectl exec -it temporaltest-admintools-xxxxx bash
tctl --namespace default namespace register
exit
```
5. перейти в директорию с манифестами
```
cd homework7/k8s
```
6. установить манифесты
```
minikube kubectl -- apply -f .
```
7. Дождаться запуска подов
```
minikube kubectl -- get pod
```
8. Запустить Postman с коллекцией из проекта
```
newman run ./homework8.postman_collection.json
```

9.Посмотреть результы можно подключившись к web-console temporal.io из браузера 
```
kubectl port-forward temporaltest-web-xxxx 8080:8080
```
