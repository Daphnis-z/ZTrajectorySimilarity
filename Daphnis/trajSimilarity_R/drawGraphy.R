tj1=trajs[[4]]
tj2=trajs[[201]]
dis=DTW(tj1,tj2)
plot(tj1,type='l',col='red',xlab='latitude',ylab='longitude',main=dis)
par(new=TRUE)
plot(tj2,type='l',col='green',xlab='',ylab='',axes=FALSE)
