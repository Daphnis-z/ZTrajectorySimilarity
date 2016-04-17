source('readTrajData.R')
trajs<-readTraj('taxiData.csv')
trajNum<-length(trajs)

#开始调用轨迹比较算法
library(SimilarityMeasures)
tj<-trajs[[25]]

t1<-Sys.time() #计时开始
for(i in 1:trajNum){
    dis<-EditDist(tj,trajs[[i]])
}
t2<-Sys.time() #计时结束

t2-t1
trajNum
(t2-t1)/trajNum

# LCSS(trajs[[25]],trajs[[1]],-1,0.05,10)
