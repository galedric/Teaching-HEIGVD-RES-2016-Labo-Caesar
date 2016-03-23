package ch.heigvd.res.caesar.protocol.frame;

import ch.heigvd.res.caesar.protocol.FrameBuilder;
import ch.heigvd.res.caesar.protocol.FrameReader;
import ch.heigvd.res.caesar.protocol.Protocol;

import java.io.IOException;

public class ServerHelloFrame extends Frame {
	public final short version;
	public final int g;
	public final int p;
	public final long serverKey;

	public ServerHelloFrame(int g, int p, long serverKey) {
		this(Protocol.VERSION, g, p, serverKey);
	}

	public ServerHelloFrame(short version, int g, int p, long serverKey) {
		this.version = version;
		this.g = g;
		this.p = p;
		this.serverKey = serverKey;
	}

	@Override
	public byte[] serialize() throws IOException {
		FrameBuilder fb = new FrameBuilder(Protocol.SERVER_HELLO);
		fb.writeShort(version);
		fb.writeInt(g);
		fb.writeInt(p);
		fb.writeLong(serverKey);
		return fb.getFrameBuffer();
	}

	public static ServerHelloFrame unserialize(byte[] frame) throws IOException {
		FrameReader fr = new FrameReader(frame, Protocol.SERVER_HELLO);
		short version = fr.readShort();
		int g = fr.readInt();
		int p = fr.readInt();
		long serverKey = fr.readLong();
		return new ServerHelloFrame(version, g, p, serverKey);
	}
}
