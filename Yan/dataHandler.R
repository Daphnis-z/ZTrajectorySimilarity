#请在环境中安装在zoo包  lof()5S
library(zoo)
#longitude  latitude  time  是数据框的属性名dataframe为名称
data<-zoo(dataframe)
#经纬度缺失值处理
data$longitude<-na.approx(data$longitude,rule=2)
data$latitude<-na.approx(data$latitude,rule=2)

#异常值检测
kmeans.result<-kmeans(dataframe,nrow(dataframe)/2)
distances<-sqrt(rowSums((dataframe-kmeans.result$centers)^2))
#ouliers算出来的异常值的行数，向量需要选择阈值
outliers<-order(distances,decreasing = T) 