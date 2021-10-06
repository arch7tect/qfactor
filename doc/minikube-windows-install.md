
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
minikube config set memory 8192
minikube config set cpus 4
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