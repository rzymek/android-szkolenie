package org.rzymek.todoexpert;

import java.io.Serializable;

public class Todo implements Serializable{
	private static final long serialVersionUID = 1L;
	public String text;
	public boolean completed;

	public Todo(String text, boolean completed) {
		this.text = text;
		this.completed = completed;
	}

	@Override
	public String toString() {
		return "[" + (completed ? 'x' : ' ') + "] " + text;
	}
}
