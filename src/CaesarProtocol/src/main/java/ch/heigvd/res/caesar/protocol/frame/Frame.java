package ch.heigvd.res.caesar.protocol.frame;

import java.io.IOException;

abstract public class Frame {
	abstract public byte[] serialize() throws IOException;
}
