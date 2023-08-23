#!/bin/bash

# dev cluster name 변경 필요 dev-mpd-eks-cluster
export EKS_CONTEXT_LOCAL="minikube"

PROFILE="LOCAL"

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


# 환경 변수 정의
case "$PROFILE" in
"LOCAL")
  # Wait for namespace mpd to be deleted
  if kubectl get ns | grep -q mpd; then
    echo "mpd namespace exists. Deleting..."
    kubectl delete ns mpd
    sleep 2
  fi

  skaffold dev -v info --profile local --platform linux/amd64 #debug --cache-artifacts=false # --digest-source='local' # --port-forward --no-prune=false
  ;;

*)
  echo "Invalid profile. Please specify PRD, DEV, LOCAL or ..."
  exit 1
  ;;
esac