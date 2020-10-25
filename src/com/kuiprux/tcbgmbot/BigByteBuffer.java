package com.kuiprux.tcbgmbot;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Deprecated //실컷 구현했는데 계산해보니 파일이 3시간만 안넘으면 안정권
public class BigByteBuffer {

	List<ByteBuffer> buffers = new ArrayList<ByteBuffer>();
	
	int indexFlag = 0;
	int subIndexFlag = 0;
	
	int posFlag = 0;
	int subPosFlag = 0;
	
	public BigByteBuffer(long length) {
		findIndex(length);
		for(int i = 0; i < indexFlag; i++) {
			ByteBuffer buffer = ByteBuffer.allocate(Integer.MAX_VALUE);
			buffers.add(buffer);
		}
		if(subIndexFlag > 0) {
			ByteBuffer buffer = ByteBuffer.allocate(subIndexFlag);
			buffers.add(buffer);
		}
	}
	
	public int getBytes(byte[] data, long readStart, int length) {
		findIndex(readStart);
		ByteBuffer buffer = buffers.get(indexFlag);
		buffer.get(subIndexFlag, data);
		int readableLength = buffer.capacity()-subIndexFlag;
		int readLength = Math.min(length, readableLength);
		if(subIndexFlag+1 < buffers.size() && length > readableLength) {
			ByteBuffer buffer1 = buffers.get(indexFlag+1);
			buffer1.get(data, readableLength, length - readableLength);
			readLength += Math.min(length-readableLength, buffer1.capacity());
		}
		return readLength;
	}
	
	public void put(byte[] data) {
		ByteBuffer buffer = buffers.get(posFlag);
		int putLength = Math.min(data.length, buffer.remaining());//TODO bytebuffer는 넣을 것보다 용량이 작으면 안넣고 오류 띄움. 용량체크 ㄱ?
		buffer.put(data, 0, putLength);
		if(data.length > putLength) {
			ByteBuffer buffer1 = buffers.get(posFlag+1);// 정해진 길이보다 많으면 bytebuffer도 오류뿜으니까 길이 체크는 생략
			buffer1.put(data, putLength, data.length-putLength); // 정해진 길이보다 많으면 bytebuffer도 오류뿜으니까 길이 체크는 생략
			posFlag++;
			subPosFlag = data.length-putLength;//정해진 길이보다 길었으면 위에서 에러났음. 여기까지 왔다는 건 모든 데이터가 다 쓰였다는 증거
		} else {
			subPosFlag += putLength;
		}
	
	}
	
	private void findIndex(long length) {
		indexFlag = -1;
		while(length > 0) {
			int intLength = (int) Math.min(length, Integer.MAX_VALUE);
			length -= intLength;
			indexFlag++;
			if(length == 0) {
				subIndexFlag = intLength;
			}
		}
	}
}
