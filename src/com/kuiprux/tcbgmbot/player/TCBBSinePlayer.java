package com.kuiprux.tcbgmbot.player;

import java.nio.ByteBuffer;

public class TCBBSinePlayer implements TCBBPlayer{

	private final int SAMPLE_RATE = 48000;
	//private final int bits = 16;
	//private final int channels = 2;

	private final int frequency = 440;

	double curI = 0;
	double curJ = 0;

	@Override
	public boolean provide(ByteBuffer buffer) {
		buffer.clear();
		createSinWaveBuffer(buffer);
		//createSimpleBuffer(buffer);
		return true;
	}

	public boolean setPlayState(PlayState state) {
		return false;
	}
/*
	boolean filled = false;
	private void createSimpleBuffer(ByteBuffer buffer) {
		while (buffer.hasRemaining()) {
			buffer.put((byte)(filled ? 127 : -127));
		}
		filled = !filled;
	}
*/	

	private void createSinWaveBuffer(ByteBuffer buffer) {
		double period = (double) SAMPLE_RATE / frequency;

		while (buffer.hasRemaining()) {
			double angle = 2.0 * Math.PI * curI / period;
			short val = (short) (Math.sin(angle) * 32767f/2f);
			putShortToBuffer(buffer, val); //right side
			putShortToBuffer(buffer, val); //left side
			curI++;
		}
	}
	
	private void putShortToBuffer(ByteBuffer buffer, short val) {
		buffer.put((byte) (val >> 8));
		buffer.put((byte) val);
	}
/*
	private String getShortToString(short val) {
		return String.format("%16s", Integer.toBinaryString(val & 0xFFFF)).replace(' ', '0');
	}
	private String getByteToString(byte val) {
		return String.format("%8s", Integer.toBinaryString(val & 0xFF)).replace(' ', '0');
	}
*/

	@Override
	public void addTransition(Transition transition) {}
}
