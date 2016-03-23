package ch.heigvd.res.caesar.protocol.frame;

import ch.heigvd.res.caesar.protocol.FrameBuilder;
import ch.heigvd.res.caesar.protocol.FrameReader;
import ch.heigvd.res.caesar.protocol.Protocol;

import java.io.IOException;

public class LoginFailFrame extends Frame {
	public final String message;

	public LoginFailFrame(String message) {
		this.message = message;
	}

	@Override
	public byte[] serialize() throws IOException {
		FrameBuilder fb = new FrameBuilder(Protocol.LOGIN_FAIL);
		fb.writeString(message);
		return fb.getFrameBuffer();
	}

	public static LoginFailFrame unserialize(byte[] frame) throws IOException {
		FrameReader fr = new FrameReader(frame, Protocol.LOGIN_FAIL);
		String message = fr.readString();
		return new LoginFailFrame(message);
	}
}
