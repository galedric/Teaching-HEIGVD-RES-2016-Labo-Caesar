package ch.heigvd.res.caesar.protocol;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CipherInputStream extends FilterInputStream {
	private long delta = 0;

	public CipherInputStream(InputStream in) {
		super(in);
	}

	public void setKey(long delta) {
		this.delta = delta;
	}

	@Override
	public int read() throws IOException {
		long r = in.read() - delta;
		if (r < 0) {
			return (int) (r + 256);
		} else {
			return (int) (r % 256);
		}
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
