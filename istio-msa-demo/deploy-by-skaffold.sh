#!/bin/bash

# dev cluster name 변경 필요 dev-istio-demo-eks-cluster
export EKS_CONTEXT_LOCAL="minikube"

PROFILE="LOCAL"

for arg in "$@"; do
  if [ "$arg" == "--local" ]; then
    PROFILE="LOCAL"
  elif [ "$arg" == "--dev" ]; then
    PROFILE="DEV"
  elif [ "$arg" == "--prd" ]; then
    PROFILE="PRD"
  fi
done

EKS_CONTEXT_VAR="EKS_CONTEXT_$PROFILE"
EKS_CONTEXT=${!EKS_CONTEXT_VAR}
CURRENT_CONTEXT=$(kubectl config current-context)

if [ "$EKS_CONTEXT" != "$CURRENT_CONTEXT" ]; then
  echo "Error: Current context $CURRENT_CONTEXT is not $EKS_CONTEXT"
  echo "Switching context from $EKS_CONTEXT"
  echo "- e.g. aws eks update-kubeconfig --name {cluster-name} --profile {profile}"
  echo "-  or kubectl config use-context $EKS_CONTEXT"
  exit 1
fi

export DOCKER_ENV="true"

#istio setting
#istioctl install --set profile=demo -y
#helm repo add istio https://istio-release.storage.googleapis.com/charts
#helm repo update


# 환경 변수 정의
case "$PROFILE" in
"LOCAL")
  # Wait for namespace istio-demo to be deleted
  if kubectl get ns | grep -q istio-demo; then
    echo "istio-demo namespace exists. Deleting..."
    kubectl delete ns istio-demo
    sleep 2
  fi

  skaffold dev -v info --profile local --platform linux/amd64 #debug --cache-artifacts=false # --digest-source='local' # --port-forward --no-prune=false
  ;;

*)
  echo "Invalid profile. Please specify PRD, DEV, LOCAL or ..."
  exit 1
  ;;
esac