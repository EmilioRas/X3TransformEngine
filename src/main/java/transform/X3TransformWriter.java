package main.java.transform;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface X3TransformWriter {
	public ByteArrayOutputStream writeTo(Object target, Class<?> type) throws IOException;
}
