package com.kuiprux.tcbgmbot.player;

import java.nio.ByteBuffer;

public class Util {

	public static final int AUDIO_CHANNEL = 2;
	public static final int BYTE_IN_A_FRAME = 2;
	public static final int SAMPLE_SIZE_IN_A_MILLIS = 48;
	public static final int BYTE_IN_A_MILLIS = AUDIO_CHANNEL*BYTE_IN_A_FRAME*SAMPLE_SIZE_IN_A_MILLIS;
	public static final int BYTE_IN_TWENTY_MILLIS = BYTE_IN_A_MILLIS*20;
	

	public static void mergeMusicBytes(ByteBuffer[] data, int interval, ByteBuffer dst) {
		mergeMusicBytes(data, new int[] {interval}, dst);
	}

	public static void mergeMusicBytes(ByteBuffer[] data, int[] intervals, ByteBuffer dst) {
		int[] addedSamples = new int[dst.capacity()/2];
		int startPoint = 0;
		for(int i = 0; i < data.length; i++) {
			startPoint += (i == 0 ? 0 : (intervals.length <= i-1 ? intervals[0] : intervals[i-1]));
			int curIndex = startPoint;
			if(startPoint*2 >= dst.capacity())
				break;
			dst.position(startPoint*2);
			data[i].position(0);
			
			while(dst.hasRemaining() && data[i].hasRemaining()) {
				addedSamples[curIndex]++;
				short tmp = dst.getShort();
				short val = (short) (((float)tmp + data[i].getShort()) / addedSamples[curIndex]);
				dst.position(dst.position()-2);
				dst.putShort((short) (val-tmp));
				curIndex++;
			}
		}
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < data.length; j++) {
				if(j >= data[j].capacity())
					break;
				System.out.print(data[j].array()[i] + "\t");
			}
			System.out.println(dst.array()[i]);
		}
	}
	
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
	}
}
