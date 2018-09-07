package com.github.leecho.cloud.config.admin.web.servlet.resource;

import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;

/**
 * Extension of ByteArrayResource with lastModified and filename.
 * 
 * @author Johannes Edmeier
 */
public class InMemoryFileResource extends ByteArrayResource {
	private final String filename;
	private final long lastModified;

	public InMemoryFileResource(String filename, String description, byte[] content,
								long lastModified) {
		super(content, description);
		this.lastModified = lastModified;
		this.filename = filename;
	}

	@Override
	public String getFilename() {
		return filename;
	}

	@Override
	public long lastModified() throws IOException {
		return lastModified;
	}
}
