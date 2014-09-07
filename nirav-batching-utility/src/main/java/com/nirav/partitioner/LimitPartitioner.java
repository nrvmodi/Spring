package com.nirav.partitioner;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;


public class LimitPartitioner implements Partitioner{

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		int start=1;
		int end=100;
		Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();
		for(int i=0;i<gridSize;i++){
			ExecutionContext context = new ExecutionContext();
			context.putInt("start", start);
			context.putInt("end", end);
			
			result.put("partition-"+i, context);
			start = end+1;
			end = end+100;
		}
		return result;
	}

}
