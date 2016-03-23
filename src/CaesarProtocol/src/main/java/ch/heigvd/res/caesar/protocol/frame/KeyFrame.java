package ch.heigvd.res.caesar.protocol.frame;

import ch.heigvd.res.caesar.protocol.FrameBuilder;
import ch.heigvd.res.caesar.protocol.FrameReader;
import ch.heigvd.res.caesar.protocol.Protocol;

import java.io.IOException;

public class KeyFrame extends Frame {
	public final int clientKey;

	public KeyFrame(int clientKey) {
		this.clientKey = clientKey;
	}

	@Override
	public byte[] serialize() throws IOException {
		FrameBuilder fb = new FrameBuilder(Protocol.KEY);
		fb.writeInt(clientKey);
		return fb.getFrameBuffer();
	}

	public static KeyFrame unserialize(byte[] frame) throws IOException {
		FrameReader fr = new FrameReader(frame, Protocol.KEY);
		int key = fr.readInt();
		return new KeyFrame(key);
	}
}
