package ch.heigvd.res.caesar.protocol;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CipherOutputStream extends FilterOutputStream {
	private long delta = 0;

	public CipherOutputStream(OutputStream out) {
		super(out);
	}

	public void setKey(long delta) {
		this.delta = delta;
	}

	public void write(int b) throws IOException {
		out.write((int)(b + delta));
	}
}
