package ch.heigvd.res.caesar.protocol.frame;

import ch.heigvd.res.caesar.protocol.FrameBuilder;
import ch.heigvd.res.caesar.protocol.FrameReader;
import ch.heigvd.res.caesar.protocol.Protocol;

import java.io.IOException;

public class ServerHelloFrame extends Frame {
	public final short version;
	public final int g;
	public final int p;
	public final int serverKey;

	public ServerHelloFrame(int g, int p, int serverKey) {
		this(Protocol.VERSION, g, p, serverKey);
	}

	public ServerHelloFrame(short version, int g, int p, int serverKey) {
		this.version = version;
		this.g = g;
		this.p = p;
		this.serverKey = serverKey;
	}

	@Override
	public byte[] serialize() throws IOException {
		FrameBuilder fb = new FrameBuilder(Protocol.MESSAGE);
		fb.writeShort(version);
		fb.writeInt(g);
		fb.writeInt(p);
		fb.writeInt(serverKey);
		return fb.getFrameBuffer();
	}

	public static ServerHelloFrame unserialize(byte[] frame) throws IOException {
		FrameReader fr = new FrameReader(frame, Protocol.MESSAGE);
		short version = fr.readShort();
		int g = fr.readInt();
		int p = fr.readInt();
		int serverKey = fr.readInt();
		return new ServerHelloFrame(version, g, p, serverKey);
	}
}
