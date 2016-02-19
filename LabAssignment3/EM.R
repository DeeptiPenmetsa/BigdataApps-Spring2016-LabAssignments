library(mclust)
lab3<-read.csv('lab3.csv')
mc<-Mclust(lab3[,1:5],4)
summary(mc)
plot(mc,what="density",dimens=c(1,2))
plot(mc,what="classification",dimens=c(1,2))
table(lab3$UNS,mc$classification)
   
