#数据读入内存
readTraj<-function(fileName){
  taxiData<-read.csv(fileName)
  trajs<-list()
  len<-as.numeric(lengths(taxiData['POLYLINE'][1]))
  
  for (i in 1:len) {
    locData<-as.character(taxiData['POLYLINE'][i,1])
    s1<-gsub("[][,]","",locData)
    s1<-strsplit(s1," ")
    trajs[[i]]<-matrix(as.numeric(unlist(s1)),lengths(s1)/2,2,byrow = TRUE)
  }
  return(trajs)
}