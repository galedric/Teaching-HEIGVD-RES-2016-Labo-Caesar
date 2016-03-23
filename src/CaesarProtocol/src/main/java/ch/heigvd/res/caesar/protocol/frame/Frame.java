package ch.heigvd.res.caesar.protocol.frame;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

abstract public class Frame {
	abstract public byte[] serialize();

}
