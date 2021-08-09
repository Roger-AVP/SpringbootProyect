package com.todosservice.enums;

public enum ExeptionMessageEnum {
	// SQL
	UNIQUE_KEY("UNIQUE KEY"), NULL_COLUMNA("NULL en la columna"), VALIDATION_FAILDER("Validation failed for argument"),

	// PSQL
	LLAVE_DUPLICADA("llave duplicada"), NULL_COLUMNA_PSQL("valor null para la columna"),

	// HTTTP
	JSON_MAPPING_EXEPTION("JsonMappingException"), REQUIRED_REQUEST_BODY_IS_MISSING("Required request body is missing"),

	// EXCEL
	// java.lang.IllegalStateException: Cannot get a STRING value from a NUMERIC
	// cell
	CANNOT_STRING_NUMERIC("Cannot get a STRING value from a NUMERIC cell");

	private String name;

	private ExeptionMessageEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
