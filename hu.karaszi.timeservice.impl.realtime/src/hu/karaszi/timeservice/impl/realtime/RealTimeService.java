package hu.karaszi.timeservice.impl.realtime;

import java.util.Date;

import hu.karaszi.timeservice.TimeService;

public class RealTimeService implements TimeService {

	@Override
	public long getCurrentTimeInMilis() {
		return System.currentTimeMillis();
	}

	@Override
	public Date getCurrentDate() {
		return new Date();
	}

}
