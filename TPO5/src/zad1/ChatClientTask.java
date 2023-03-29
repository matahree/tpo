
package zad1;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;



public class ChatClientTask extends FutureTask<String>{
	
	public ChatClientTask (Callable<String> callable) {
		super(callable);
	}

	 public static ChatClientTask  create(ChatClient c, List<String> msgs, int wait) {
		 
		 Callable<String> callable = ()->{
			ChatClient client = c;
			List<String> requestList = msgs;
			
			client.login();
		      if (wait != 0) Thread.sleep(wait);

		        for(String message : requestList) {

		          c.send(message);
		          if (wait != 0) Thread.sleep(wait);
		        }
		      c.logout();
		      if (wait != 0) Thread.sleep(wait);
		      return client.getChartView();
		 };
		 return new ChatClientTask(callable);
	 } 
	
	

}
