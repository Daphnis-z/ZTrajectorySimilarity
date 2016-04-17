#开始计算
#轨迹数据放在矩阵‘trajs’中
#起始下标‘startIx’，终止下标‘endIx’
library(SimilarityMeasures)
tj<-trajs[[25]]
for(i in startIx:endIx){
    dis<-EditDist(tj,trajs[[i]])
}