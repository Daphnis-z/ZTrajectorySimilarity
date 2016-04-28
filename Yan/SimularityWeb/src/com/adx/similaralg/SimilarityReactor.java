package com.adx.similaralg;

import com.adx.entity.Trajectory;
/**
 * 计算相似度的接口
 * @author Agnes
 *可用多种算法来实现相似度计算。获取相似度
 */
public interface SimilarityReactor {
	public double getSimilarity(Trajectory objTraj,Trajectory testTraj);
}
