package ch.heigvd.res.caesar.protocol;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CipherOutputStream extends FilterOutputStream {
	private int delta = 0;

	public CipherOutputStream(OutputStream out) {
		super(out);
	}

	public void setKey(int delta) {
		this.delta = delta;
	}

	public void write(int b) throws IOException {
		out.write(b + delta);
	}
}
