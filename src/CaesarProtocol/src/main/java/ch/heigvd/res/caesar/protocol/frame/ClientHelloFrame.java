package ch.heigvd.res.caesar.protocol.frame;

import ch.heigvd.res.caesar.protocol.FrameBuilder;
import ch.heigvd.res.caesar.protocol.Protocol;

public class ClientHelloFrame extends Frame {
	public final int version = 1;

	@Override
	public byte[] serialize() {
		FrameBuilder frame = new FrameBuilder();
		frame.writeByte(Protocol);
		frame.wr
		return new byte[0];
	}
}
