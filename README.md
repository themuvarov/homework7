# saga temporalio demo

1. Клонировать репозиторий проекта
   git clone git@github.com:themuvarov/homework7.git

   Код с бизнес-логикой и логикой компенсации Saga тут
   https://github.com/themuvarov/homework7/blob/main/posting-client/src/main/java/demo/workflow/posting/PostOperationWorkflowImpl.java#L36

2. Установить Temporal.io
   git clone https://github.com/temporalio/helm-charts.git
   cd helm-charts
   helm install     --set server.replicaCount=1     --set cassandra.config.cluster_size=1     --set prometheus.enabled=false     --set grafana.enabled=false     --set elasticsearch.enabled=false     temporaltest . --timeout 15m
  
3. Дождаться запуска pod temporal.io
4. Создать namespace default
      kubectl exec -it temporaltest-admintools-xxxxx bash
      tctl --namespace default namespace register
      exit
5. перейти в директорию с манифестами
   cd homework7/k8s

6. установить манифесты
   minikube kubectl -- apply -f .

7. Дождаться запуска подов
   minikube kubectl -- get pod

8. Запустить Postman с коллекцией из проекта
   newman run ./homework7.postman_collection.json

10. Normal process - нормальное завершение

11. Saga compensation - на этапе создания Outward произошла ошибка (эмуляция ошибки если длина поля agent четная), компенсация транзакции в сервисе billing

12. Посмотреть результы можно подключившись к web-console temporal.io из браузера 
    kubectl port-forward temporaltest-web-xxxx 8080:8080