#!/bin/bash

istioctl uninstall -y --purge
kubectl delete ns istio-system
kubectl delete ns istio-demo