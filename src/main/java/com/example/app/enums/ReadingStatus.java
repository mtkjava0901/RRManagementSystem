package com.example.app.enums;

public enum ReadingStatus {

	// UserBook.statusを扱うためのクラス

	WANT_TO_READ("読みたい"), UNREAD("未読"), READING("読書中"), READ("既読"), UNSPECIFIED("未指定");

	private final String label;

	ReadingStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	// DB文字列から変換するユーティリティ
	public static ReadingStatus from(String dbValue) {
		for (ReadingStatus status : values()) {
			if (status.label.equals(dbValue)) {
				return status;
			}
		}
		return UNSPECIFIED;
	}
}
