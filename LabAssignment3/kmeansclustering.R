lab3<-read.csv("lab3.csv")
Lab3<-lab3
Lab3$UNS<-NULL
km<-kmeans(Lab3,4,41)
table(lab3$UNS,km$cluster)
plot(Lab3[c("STG","SCG","STR","LPR","PEG")],col=km$cluster)
