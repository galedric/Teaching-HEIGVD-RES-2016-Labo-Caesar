package ch.heigvd.res.caesar.protocol.frame;

import ch.heigvd.res.caesar.protocol.FrameBuilder;
import ch.heigvd.res.caesar.protocol.FrameReader;
import ch.heigvd.res.caesar.protocol.Protocol;

import java.io.IOException;

public class SendFrame extends Frame {
	private final String message;

	public SendFrame(String message) {
		this.message = message;
	}

	@Override
	public byte[] serialize() throws IOException {
		FrameBuilder fb = new FrameBuilder(Protocol.MESSAGE);
		fb.writeString(message);
		return fb.getFrameBuffer();
	}

	public static SendFrame unserialize(byte[] frame) throws IOException {
		FrameReader fr = new FrameReader(frame, Protocol.MESSAGE);
		String message = fr.readString();
		return new SendFrame(message);
	}
}
