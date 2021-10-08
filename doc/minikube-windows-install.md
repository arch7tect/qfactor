
[Скачать kubectl](https://storage.googleapis.com/kubernetes-release/release/v1.22.0/bin/windows/amd64/kubectl.exe)
Положить его в каталог из PATH (!!ДО!! kubectl от Docker Desktop - echo %PATH%) 
проверить 
```shell
> kubectl version --client
Client Version: version.Info{Major:"1", Minor:"22", GitVersion:"v1.22.0", GitCommit:"c2b5237ccd9c0f1d600d3072634ca66cefdf272f", GitTreeState:"clean", BuildDate:"202
1-08-04T18:03:20Z", GoVersion:"go1.16.6", Compiler:"gc", Platform:"windows/amd64"}
```

Проверить, что работает Hyper-V
из PowerShell с правами Administrator
```shell
> Enable-WindowsOptionalFeature -Online -FeatureName Microsoft-Hyper-V -All
```

[Скачать и проинсталлировать chocotaley](https://chocolatey.org/install)

из PowerShell с правами Administrator:
```shell
> choco install minikube
```

Стартуем из PowerShell с правами Administrator
```shell
> minikube config set memory 8192
> minikube config set cpus 4
> minikube start --vm-driver=hyperv
```

Проверяем
```shell
> minikube status
minikube
type: Control Plane
host: Running
kubelet: Running
apiserver: Running
kubeconfig: Configured
```

Проверяем dashboard 
```shell
> minikube dashboard
```

Полезные команды для памяти (не нужно выполнять!)
```shell
minikube stop
minikube delete
minikube config set kubernetes-version 1.16.2
minikube config set vm-driver kvm2
minikube config set container-runtime crio
minikube config view
```

Установка Helm
```shell
> choco install kubernetes-helm
```

Установка Keycloak
```shell
> helm repo add codecentric https://codecentric.github.io/helm-charts
> kubectl create namespace keycloak
> helm install keycloak --namespace keycloak codecentric/keycloak
```
Проверяем
```shell
> kubectl get all -n keycloak
NAME                        READY   STATUS    RESTARTS   AGE
pod/keycloak-0              1/1     Running   0          2m37s
pod/keycloak-postgresql-0   1/1     Running   0          2m37s

NAME                                   TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)                    AGE
service/keycloak-headless              ClusterIP   None            <none>        80/TCP                     2m37s
service/keycloak-http                  ClusterIP   10.97.185.128   <none>        80/TCP,8443/TCP,9990/TCP   2m37s
service/keycloak-postgresql            ClusterIP   10.97.164.16    <none>        5432/TCP                   2m37s
service/keycloak-postgresql-headless   ClusterIP   None            <none>        5432/TCP                   2m37s

NAME                                   READY   AGE
statefulset.apps/keycloak              1/1     2m37s
statefulset.apps/keycloak-postgresql   1/1     2m37s
```
Пробрасываем порт наружу
```shell
kubectl -n keycloak port-forward service/keycloak-http 8080:80
```
[Открываем браузер](http://localhost:8080/auth/)

Инсталляция postgres
```shell
> kubectl apply -f k8s/postgres-cm.yaml
> kubectl apply -f k8s/postgres-secret.yaml
> kubectl apply -f k8s/postgres-pvc.yaml
> kubectl apply -f k8s/postgres-deployment.yaml
> kubectl apply -f k8s/postgres-svc.yaml
```
Проверяем из-под Administrator
```shell
> minikube service postgres --url
http://172.23.24.68:30143
```

Инсталляция ELK 
```shell
> helm repo add elastic https://helm.elastic.co
> helm repo update
```
Elasticsearch
```shell
> helm install elasticsearch elastic/elasticsearch -f k8s/elasticsearch-values.yaml
```
Проверяем
```shell
> kubectl get all -l release=elasticsearch
NAME                         READY   STATUS     RESTARTS   AGE
pod/elasticsearch-master-0   0/1     Init:0/1   0          55s
pod/elasticsearch-master-1   0/1     Init:0/1   0          55s
pod/elasticsearch-master-2   0/1     Init:0/1   0          55s

NAME                                    TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)             AGE
service/elasticsearch-master            ClusterIP   10.106.57.40   <none>        9200/TCP,9300/TCP   55s
service/elasticsearch-master-headless   ClusterIP   None           <none>        9200/TCP,9300/TCP   55s

NAME                                    READY   AGE
statefulset.apps/elasticsearch-master   0/3     55s
kubectl port-forward service/elasticsearch-master 9200

> curl http://localhost:9200
StatusCode        : 200
StatusDescription : OK
Content           : {
                      "name" : "elasticsearch-master-0",
                      "cluster_name" : "elasticsearch",
                      "cluster_uuid" : "DYkTv5-XQciAkd7vNekaMA",
...                      
```

Kibana
```shell
> helm install kibana elastic/kibana  -f k8s/kibana-values.yaml --set fullnameOverride=qfactor-kibana
```
Проверяем
```shell
kubectl get all -l release=kibana
NAME                                  READY   STATUS    RESTARTS   AGE
pod/qfactor-kibana-7779cc65db-6btc2   1/1     Running   0          3m7s

NAME                     TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)    AGE
service/qfactor-kibana   ClusterIP   10.109.242.112   <none>        5601/TCP   3m7s

NAME                             READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/qfactor-kibana   1/1     1            1           3m7s

NAME                                        DESIRED   CURRENT   READY   AGE
replicaset.apps/qfactor-kibana-7779cc65db   1         1         1       3m7s
> kubectl port-forward service/qfactor-kibana 5601
```
Logstash
```shell
helm install -f k8s/logstash-values.yaml logstash elastic/logstash --set fullnameOverride=qfactor-logstash
> kubectl get all -l chart=logstash
NAME                     READY   STATUS    RESTARTS   AGE
pod/qfactor-logstash-0   0/1     Pending   0          2m46s

NAME                                TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)                       AGE
service/qfactor-logstash            ClusterIP   10.110.114.39   <none>        5000/TCP,9600/TCP,12201/UDP   2m46s
service/qfactor-logstash-headless   ClusterIP   None            <none>        9600/TCP                      2m46s

NAME                                READY   AGE
statefulset.apps/qfactor-logstash   0/1     2m46s
```
Docker registry
```shell
> minikube addons enable registry
> kubectl -n kube-system get pod
> kubectl -n kube-system get svc
```