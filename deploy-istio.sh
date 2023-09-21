#!/bin/bash

#helm repo add kiali https://kiali.org/helm-charts
##helm repo add istio https://istio-release.storage.googleapis.com/charts
#helm repo update
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

# kubectl label namespace istio-demo istio-injection=enabled
# kubectl -n istio-demo rollout restart deploy

case "$PROFILE" in
"LOCAL")
    kubectl delete kiali kiali -n kiali-operator
    minikube addons enable istio-provisioner --images="IstioOperator=istio/operator:1.18.1"
    minikube addons enable istio
    kubectl patch iop example-istiocontrolplane -n istio-system --type='json' -p='[{"op": "replace", "path": "/spec/profile", "value":"default"}]'
    skaffold dev --profile local -m custom-resources,kiali -f istio-skaffold.yaml -v info --platform linux/amd64 
    ;;
"EDU")
    skaffold dev --profile edu -m ingress,custom-resources,kiali -f istio-skaffold.yaml -v info --platform linux/amd64
    ;;
"PRD")
    skaffold run -m ingress,custom-resources,kiali -f istio-skaffold.yaml -v info --platform linux/amd64
    ;;
*)
    echo "Invalid profile. Please specify PRD, EDU, LOCAL or ..."
    exit 1
    ;;
esac