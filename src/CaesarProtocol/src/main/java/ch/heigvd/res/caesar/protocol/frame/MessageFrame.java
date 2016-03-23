package ch.heigvd.res.caesar.protocol.frame;

import ch.heigvd.res.caesar.protocol.FrameBuilder;
import ch.heigvd.res.caesar.protocol.FrameReader;
import ch.heigvd.res.caesar.protocol.Protocol;

import java.io.IOException;

public class MessageFrame extends Frame {
	private final String author;
	private final String message;

	public MessageFrame(String author, String message) {
		this.author = author;
		this.message = message;
	}

	@Override
	public byte[] serialize() throws IOException {
		FrameBuilder fb = new FrameBuilder(Protocol.MESSAGE);
		fb.writeString(author);
		fb.writeString(message);
		return fb.getFrameBuffer();
	}

	public static MessageFrame unserialize(byte[] frame) throws IOException {
		FrameReader fr = new FrameReader(frame, Protocol.MESSAGE);
		String author = fr.readString();
		String message = fr.readString();
		return new MessageFrame(author, message);
	}
}
