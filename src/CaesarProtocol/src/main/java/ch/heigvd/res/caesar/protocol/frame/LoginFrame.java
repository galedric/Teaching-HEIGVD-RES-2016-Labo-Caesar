package ch.heigvd.res.caesar.protocol.frame;

import ch.heigvd.res.caesar.protocol.FrameBuilder;
import ch.heigvd.res.caesar.protocol.FrameReader;
import ch.heigvd.res.caesar.protocol.Protocol;

import java.io.IOException;

public class LoginFrame extends Frame {
	public final String username;

	public LoginFrame(String username) {
		this.username = username;
	}

	@Override
	public byte[] serialize() throws IOException {
		FrameBuilder fb = new FrameBuilder(Protocol.LOGIN);
		fb.writeString(username);
		return fb.getFrameBuffer();
	}

	public static LoginFrame unserialize(byte[] frame) throws IOException {
		FrameReader fr = new FrameReader(frame, Protocol.LOGIN);
		String username = fr.readString();
		return new LoginFrame(username);
	}
}
