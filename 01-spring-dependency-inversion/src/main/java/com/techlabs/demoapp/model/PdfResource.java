package com.techlabs.demoapp.model;

import org.springframework.stereotype.Component;

@Component
public class PdfResource implements Resource {

	@Override
	public String getResourceMaterial() {
		return "sending video resources";
	}

}
