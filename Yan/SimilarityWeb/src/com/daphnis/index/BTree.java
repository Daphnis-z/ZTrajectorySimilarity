package com.daphnis.index;

public interface BTree { 
	/** ������Ŀ����ӽ��Ľڵ�	 */
	public Object findClosestNode(double key);
	
	/** �Ƴ��ڵ�	 */
	public void removeNode(double key);
	
	/** ������߸��£�����Ѿ����ڣ��͸��£��������	 */
	public void insertOrUpdate(double key, Object obj);
} 