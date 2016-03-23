package ch.heigvd.res.caesar.protocol;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CipherInputStream extends FilterInputStream {
	private int delta = 0;

	public CipherInputStream(InputStream in) {
		super(in);
	}

	public void setKey(int delta) {
		this.delta = delta;
	}

	@Override
	public int read() throws IOException {
		return (in.read() - delta) % 256;
	}

	@Override
	public int read(byte b[], int off, int len) throws IOException {
		int bytes = in.read(b, off, len);
		for (int i = 0; i < len; i++) {
			b[off + i] -= delta;
		}
		return bytes;
	}
}
