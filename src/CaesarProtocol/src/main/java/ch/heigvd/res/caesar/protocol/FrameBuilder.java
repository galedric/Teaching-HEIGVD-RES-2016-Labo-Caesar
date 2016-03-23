package ch.heigvd.res.caesar.protocol;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class FrameBuilder extends DataOutputStream {
	public FrameBuilder() {
		super(new ByteArrayOutputStream());
	}

	public byte[] getFrameBuffer() {
		return ((ByteArrayOutputStream) out).toByteArray();
	}
}
