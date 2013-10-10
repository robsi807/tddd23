package com.tddd23.blokz;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class Time {

	float sec;

	public Time() {
		sec = 0;
	}
	
	public Time(float sec) {
		this.sec = sec;
	}

	public void addTime(float sec) {
		this.sec += sec;
	}

	public float getSeconds() {
		return sec;
	}

	public String toString() {
		long sec = (long) (this.sec * 1000);
		return String.format(
				"%d:%02d:%03d",
				TimeUnit.MILLISECONDS.toMinutes(sec),
				TimeUnit.MILLISECONDS.toSeconds(sec)%60,
				TimeUnit.MILLISECONDS.toMillis(sec)%1000);
	}
	
	public Time getStopTime(){
		return new Time(sec);
	}
}
