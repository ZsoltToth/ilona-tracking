package uni.miskolc.ips.ilona.tracking.controller.model;

import java.util.ArrayList;
import java.util.Collection;

public class ExecutionResultDTO {

	/**
	 * 100: OK <br>
	 * 200: Parameter error <br>
	 * 300: Validity error <br>
	 * 400: Service error <br>
	 * 500: Server error (Timeout in the ajax)<br>
	 * 600+: Controller defines errors, arbitrary code and meaning
	 */
	private int responseState;

	private Collection<String> messages;

	public ExecutionResultDTO() {

	}

	public ExecutionResultDTO(int responseState, Collection<String> messages) {
		super();
		this.responseState = responseState;
		this.messages = messages;
	}


	public void addMessage(String message) {
		if (message == null) {
			return;
		}
		if (messages != null) {
			messages.add(message);
		} else {
			messages = new ArrayList<String>();
			messages.add(message);
		}
	}

	public Collection<String> getMessages() {
		return messages;
	}

	public void setMessages(Collection<String> messages) {
		this.messages = messages;
	}

	public int getResponseState() {
		return responseState;
	}

	public void setResponseState(int responseState) {
		this.responseState = responseState;
	}

}
