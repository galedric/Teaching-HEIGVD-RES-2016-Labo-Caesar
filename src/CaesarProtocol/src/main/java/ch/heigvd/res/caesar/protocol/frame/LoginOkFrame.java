package ch.heigvd.res.caesar.protocol.frame;

import ch.heigvd.res.caesar.protocol.FrameBuilder;
import ch.heigvd.res.caesar.protocol.FrameReader;
import ch.heigvd.res.caesar.protocol.Protocol;

import java.io.IOException;

public class LoginOkFrame extends Frame {
	public final int users;

	public LoginOkFrame(int users) {
		this.users = users;
	}

	@Override
	public byte[] serialize() throws IOException {
		FrameBuilder fb = new FrameBuilder(Protocol.LOGIN_OK);
		fb.writeInt(users);
		return fb.getFrameBuffer();
	}

	public static LoginOkFrame unserialize(byte[] frame) throws IOException {
		FrameReader fr = new FrameReader(frame, Protocol.LOGIN_OK);
		int users = fr.readInt();
		return new LoginOkFrame(users);
	}
}
