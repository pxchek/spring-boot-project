# spring-boot-project

08/16/2022

Happy Path (Client -> Ingress Controller -> Services -> Deployments -> Pods -> PersistenceVolumeClaim -> PersistenceVolume

```
pxchek0@skullcandy:/$ k get ingress,ingressclass,services,deployments,pods,pv,pvc
NAME                                        CLASS           HOSTS   ADDRESS     PORTS   AGE
ingress.networking.k8s.io/minimal-ingress   nginx-example   *       127.0.0.1   80      5h49m

NAME                                           CONTROLLER             PARAMETERS   AGE
ingressclass.networking.k8s.io/public          k8s.io/ingress-nginx   <none>       6h16m
ingressclass.networking.k8s.io/nginx-example   k8s.io/ingress-nginx   <none>       5h50m

NAME                 TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)    AGE
service/kubernetes   ClusterIP   10.152.183.1     <none>        443/TCP    9d
service/db           ClusterIP   10.152.183.22    <none>        5432/TCP   6h26m
service/backend      ClusterIP   10.152.183.227   <none>        8080/TCP   6h23m

NAME                      READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/db        1/1     1            1           6h33m
deployment.apps/backend   1/1     1            1           6h25m

NAME                          READY   STATUS    RESTARTS   AGE
pod/db-5cc8465c4b-ls585       1/1     Running   0          6h33m
pod/backend-7c7bd8657-zms4l   1/1     Running   0          6h25m

NAME                     CAPACITY   ACCESS MODES   RECLAIM POLICY   STATUS   CLAIM            STORAGECLASS   REASON   AGE
persistentvolume/db-pv   1Gi        RWO            Retain           Bound    default/db-pvc                           6h35m

NAME                           STATUS   VOLUME   CAPACITY   ACCESS MODES   STORAGECLASS   AGE
persistentvolumeclaim/db-pvc   Bound    db-pv    1Gi        RWO                           6h34m
pxchek0@skullcandy:/$
```
