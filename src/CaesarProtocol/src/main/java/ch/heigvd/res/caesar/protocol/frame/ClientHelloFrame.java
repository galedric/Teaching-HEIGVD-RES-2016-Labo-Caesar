package ch.heigvd.res.caesar.protocol.frame;

import ch.heigvd.res.caesar.protocol.FrameBuilder;
import ch.heigvd.res.caesar.protocol.FrameReader;
import ch.heigvd.res.caesar.protocol.Protocol;

import java.io.IOException;

public class ClientHelloFrame extends Frame {
	public final short version;

	public ClientHelloFrame() {
		this(Protocol.VERSION);
	}

	public ClientHelloFrame(short version) {
		this.version = version;
	}

	@Override
	public byte[] serialize() throws IOException {
		FrameBuilder fb = new FrameBuilder(Protocol.CLIENT_HELLO);
		fb.writeByte(version);
		return fb.getFrameBuffer();
	}

	public static ClientHelloFrame unserialize(byte[] frame) throws IOException {
		FrameReader fr = new FrameReader(frame, Protocol.CLIENT_HELLO);
		short version = fr.readByte();
		return new ClientHelloFrame(version);
	}
}
