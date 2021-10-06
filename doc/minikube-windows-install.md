
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