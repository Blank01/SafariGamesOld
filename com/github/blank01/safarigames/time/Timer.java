package com.github.blank01.blank01.time;

import java.util.concurrent.CountDownLatch;

public class Timer {

	private CountDown countDown;
	private CountDownLatch latch;
	private boolean running;
	public Timer(int seconds){
		latch=new CountDownLatch(1);
		countDown=new CountDown(latch, seconds);
		running=false;
	}
	public Timer(int seconds, String name) {
		this(seconds);
		this.countDown=new CountDown(latch, seconds, name);
	}
	public void setCustomMessage(String s){
		this.countDown.setCustomCountdownMessage(s);
	}
	public void setCustomEndMessage(String s){
		this.countDown.setCustomEndMessage(s);
	}
	
	//starts the timer and waits for it to end
	public void runAndWait(){
		try {
			running=true;
			countDown.run();
			
			latch.await();
			
		} catch (InterruptedException e) {
			latch.countDown();
			running=false;
		}	
		latch.countDown();
		running=false;
	}
	public boolean isRunning(){
		return running;
	}
	public void end() {
		this.countDown.end();
		running=false;
	}
	public void editTime(int seconds) {
		this.countDown.editTime(seconds);
	}
	public void interrupt() {
		this.countDown.interrupt();
	}
	public int getTimeLeft() {
		return this.countDown.getTimeLeft();
	}
}
