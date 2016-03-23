package ch.heigvd.res.caesar.protocol;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FrameBuilder extends DataOutputStream {
	public FrameBuilder(byte frameType) throws IOException {
		super(new ByteArrayOutputStream());
		writeByte(frameType);
	}

	public void writeString(String str) throws IOException {
		byte[] buf = str.getBytes(StandardCharsets.UTF_8);
		if (buf.length > 0x7FFF) throw new IllegalArgumentException("String is too long");

		writeShort((short) buf.length);
		write(buf);
	}

	public byte[] getFrameBuffer() {
		return ((ByteArrayOutputStream) out).toByteArray();
	}
}
