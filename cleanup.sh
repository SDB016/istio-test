#!/bin/bash

PROFILE="LOCAL"

for arg in "$@"; do
  if [ "$arg" == "--local" ]; then
    PROFILE="LOCAL"
  elif [ "$arg" == "--edu" ]; then
    PROFILE="EDU"
  elif [ "$arg" == "--prd" ]; then
    PROFILE="PRD"
  fi
done

istioctl uninstall -y --purge
kubectl delete ns istio-system
kubectl delete ns istio-demo

case "$PROFILE" in
"LOCAL")
    kubectl delete ns istio-operator
    kubectl delete ns kiali-operator
    minikube addons disable istio-provisioner
    minikube addons disable istio
    ;;
esac