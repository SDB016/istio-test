#!/bin/bash

minikube addons enable istio-provisioner --images="IstioOperator=istio/operator:1.18.1"  # reference for supported istio version for kubernetes: https://istio.io/latest/docs/releases/supported-releases/
minikube addons enable istio

# echo "If your environment is minikube, execute 'minikube tunnel --cleanup' for ingressgateway external ip"

if [[ "$@" == *"--kiali"* ]]; then
  # echo "'istioctl dashboard kiali' can be used to access kiali dashboard"
  skaffold dev -p local -v info
else
  skaffold dev -m update-policy-demo -p local -v info
fi


# minikube addons disable istio-provisioner
# minikube addons disable istio
