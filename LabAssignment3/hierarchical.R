lab3<-read.csv("lab3.csv")
idx<-sample(1:dim(lab3)[1],50)
Lab3<-lab3[idx,]
Lab3$UNS<-NULL
hc<-hclust(dist(Lab3),method="ave")
plot(hc,hang=-1,labels=Lab3$UNS)
rect.hclust(hc,k=4)
groups<-cutree(hc,k=4)
