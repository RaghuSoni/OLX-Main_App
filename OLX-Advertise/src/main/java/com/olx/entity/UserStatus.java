package com.olx.entity;

public enum UserStatus {
	ACTIVE(0), INAVTIVE(1);

	private final int value;

	UserStatus(final int newValue) {
		value = newValue;
	}

	public int getValue() {
		return value;
	}

}
