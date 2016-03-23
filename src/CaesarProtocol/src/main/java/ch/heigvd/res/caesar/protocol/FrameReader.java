package ch.heigvd.res.caesar.protocol;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FrameReader extends DataInputStream {
	public FrameReader(byte[] frame, byte frameType) throws IOException {
		super(new ByteArrayInputStream(frame));
		if (readByte() != frameType) {
			throw new IllegalArgumentException("Frame is not of the expected type");
		}
	}

	public String readString() throws IOException {
		short len = readShort();
		byte[] buf = new byte[len];
		readFully(buf);
		return new String(buf, StandardCharsets.UTF_8);
	}
}
