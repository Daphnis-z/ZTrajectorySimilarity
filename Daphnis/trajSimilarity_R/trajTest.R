source('readTrajData.r')
trajs<-readTraj('taxiData.csv')
trajNum<-length(trajs)

#开始计算
library(SimilarityMeasures)
tj<-trajs[[25]]
for(i in 1:trajNum){
    dis<-EditDist(tj,trajs[[i]])
}
#2022