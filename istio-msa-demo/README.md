# istio-test
istio 학습 레포지토리


# 사전 필요 요소
## istio
1. Istio 릴리스 페이지로 이동하여 OS용 설치 파일을 다운로드하거나 최신 릴리스(Linux 또는 macOS)를 자동으로 다운로드하여 압축을 푼다:
```bash
$ curl -L https://istio.io/downloadIstio | sh -
```
> **_NOTE:_** 위의 명령어는 Istio의 최신 릴리스를 다운로드한다. 명령줄에 변수를 전달하여 특정 버전을 다운로드하거나 프로세서 아키텍처를 재정의할 수 있다. 예를 들어 x86_64 아키텍처용 Istio 1.19.0을 다운로드하려면 다음을 실행:

> ```bash
> $ curl -L https://istio.io/downloadIstio | ISTIO_VERSION=1.19.0 TARGET_ARCH=x86_64 sh -
> ```
2. Istio 패키지 디렉토리로 이동한다. 예를 들어 패키지가 Istio-1.19.0인 경우:
```bash
$ cd istio-1.19.0
```
3. istioctl 클라이언트를 경로(Linux 또는 macOS)에 추가:
```bash
$ export PATH=$PWD/bin:$PATH
```
Reference link: [https://istio.io/latest/docs/setup/getting-started/#download](https://istio.io/latest/docs/setup/getting-started/#download)

## Minikube
먼저 minikube driver 설치가 필요
### Intel Chip
```bash 
$ brew install hyperkit
```
### Apple Chip
[Docker install for mac](https://docs.docker.com/desktop/install/mac-install/)

그 후 Minikube를 설치한다.
```bash
$ brew install minikube
```

## Kubectl
```bash
$ brew install kubernetes-cli
```

## Skaffold
```bash
$ brew install skaffold
```

## Helm
```bash
$ brew install helm
```

# 사용법
## Minikube 시작
```bash
$ minikube start
```
## istio 설치 및 활성화
```bash
$ ./deploy-istio.sh --local
```
## demo 설치 및 활성화
```bash
$ ./deploy-by-skaffold.sh --local
```
## kiali dashboard
```bash
$ istioctl dashboard kiali
```
## Service tunnel open
```bash
$ minikube service istio-ingressgateway -n istio-system
```
