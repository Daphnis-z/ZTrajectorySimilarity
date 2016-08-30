package com.daphnis.index;

public interface BTree { 
	/** 查找与目标最接近的节点	 */
	public Object findClosestNode(double key);
	
	/** 移除节点	 */
	public void removeNode(double key);
	
	/** 插入或者更新，如果已经存在，就更新，否则插入	 */
	public void insertOrUpdate(double key, Object obj);
} 