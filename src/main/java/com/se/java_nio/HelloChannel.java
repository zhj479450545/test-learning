package com.se.java_nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

public class HelloChannel {
	
	@Test
	public void test(){		
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile("F:/橙红文档/各种账号密码.txt", "rw");
			FileChannel fileChannel = randomAccessFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(48);
			try {
				int bytesRead = fileChannel.read(buf);
				while(-1 != bytesRead){
					System.out.println("Read " + bytesRead);
					buf.flip();
					while(buf.hasRemaining()){
						System.out.print((char)buf.get());
					}
					buf.clear();
					bytesRead = fileChannel.read(buf);
					System.out.println();
				}
				randomAccessFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

