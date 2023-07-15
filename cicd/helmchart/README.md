#Basic commands
#install with
'helm install initialrelease myapp --values myapp/values.yaml'
#uninstall with
'helm uninstall initialrelease myapp'

#Additional commands
#create backbone of helmchart
'helm create myapp'
#helm upgrade
'helm upgrage initialrelease myapp --values myapp/values.yaml'
#watch packages
'helm ls'
#for prod use:
'helm install initialrelease myapp --values myapp/values.yaml'
#for dev use:
'kubectl create namespace myapp'
'helm install initialrelease myapp --values myapp/values-dev.yaml'