package com.tddd23.blokz;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class Time {

	private long millis;

	public Time() {
		millis = 0;
	}
	
	public Time(long l) {//Millis
		this.millis = l;
	}

	public void setTime(long millis) {
		this.millis = millis;
	}

	public void addTime(float sec) {
		this.millis += sec;
	}

	public long getMillis() {
		return millis;
	}

	public String toString() {
		if(millis ==-1)
			return "No record";
		return String.format(
				"%d:%02d:%03d",
				TimeUnit.MILLISECONDS.toMinutes(millis),
				TimeUnit.MILLISECONDS.toSeconds(millis)%60,
				TimeUnit.MILLISECONDS.toMillis(millis)%1000);
	}
	
	public Time getCopyTime(){
		return new Time(millis);
	}
}
