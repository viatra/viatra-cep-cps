package hu.karaszi.timeservice;

import java.util.Date;

public interface TimeService {
	public long getCurrentTimeInMilis();
	public Date getCurrentDate();
}
