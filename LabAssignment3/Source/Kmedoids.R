library(cluster)
lab3<-read.csv('lab3.csv')
pam.result<-pam(lab3,4)
table(pam.result$clustering,lab3$UNS)
layout(matrix(c(1,2),1,2))
plot(pam.result)

