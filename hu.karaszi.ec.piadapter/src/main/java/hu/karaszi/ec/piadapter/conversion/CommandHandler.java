package hu.karaszi.ec.piadapter.conversion;

import hu.karaszi.ec.piadapter.centralunitproxy.dto.CommandDTO;

public interface CommandHandler {
	public void handleCommand(CommandDTO command);
}
