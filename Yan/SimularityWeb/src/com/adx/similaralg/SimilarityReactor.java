package com.adx.similaralg;

import com.adx.entity.Trajectory;
/**
 * �������ƶȵĽӿ�
 * @author Agnes
 *���ö����㷨��ʵ�����ƶȼ��㡣��ȡ���ƶ�
 */
public interface SimilarityReactor {
	public double getSimilarity(Trajectory objTraj,Trajectory testTraj);
}
