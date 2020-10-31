package com.kuiprux.tcbgmbot.player;

import java.nio.ByteBuffer;

public class Util {

	public static void mergeMusicBytes(ByteBuffer[] data, int interval, ByteBuffer dst) {
		mergeMusicBytes(data, new int[] {interval}, dst);
	}

	public static void mergeMusicBytes(ByteBuffer[] data, int[] intervals, ByteBuffer dst) {
		int[] addedSamples = new int[dst.capacity()/2];
		int startPoint = 0;
		for(int i = 0; i < data.length; i++) {
			startPoint += (i == 0 ? 0 : (intervals.length <= i-1 ? intervals[0] : intervals[i-1]));
			int curIndex = startPoint;
			dst.position(startPoint*2);
			data[i].position(0);
			
			while(dst.hasRemaining()) {
				addedSamples[curIndex]++;
				short val = (short) (((float)dst.getShort() + data[i].getShort()) / addedSamples[curIndex]);
				dst.position(dst.position()-2);
				dst.putShort(val);
				curIndex++;
			}
		}
	}
	/*
	public static void main(String[] args) {
		ByteBuffer buf = ByteBuffer.allocate(40);
		ByteBuffer[] data = new ByteBuffer[8];
		for(int i = 0; i < data.length; i++) {
			ByteBuffer b = ByteBuffer.allocate(100);
			while(b.hasRemaining()) {
				b.putShort((short) (i*10+10));
			}
			data[i] = b;
		}
		
		mergeMusicBytes(data, 2, buf);
		buf.position(0);
		while(buf.hasRemaining()) {
			System.out.println(buf.getShort());
		}
	}*/
}
