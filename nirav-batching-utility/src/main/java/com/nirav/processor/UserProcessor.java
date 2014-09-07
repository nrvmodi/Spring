package com.nirav.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nirav.User;



@Component("itemProcessor")
@Scope(value = "step")
public class UserProcessor  implements ItemProcessor<User, User>{
	
	@Override
	public User process(User item) throws Exception {
		System.out.println(item.getId()+" "+item.getUsername());
		return item;
	}

}
