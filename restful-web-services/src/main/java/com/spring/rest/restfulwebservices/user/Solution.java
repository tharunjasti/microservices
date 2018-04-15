package com.spring.rest.restfulwebservices.user;

class Stack<T> {

	T data;

	T item;

	T status;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getItem() {
		return item;
	}

	public void setItem(T item) {
		this.item = item;
	}

	public T getStatus() {
		return status;
	}

	public void setStatus(T status) {
		this.status = status;
	}

}

public class Solution {
	public static void main(String[] args) {
		Stack st = new Stack();
		st.setData(new String());
		st.setItem(34);

		st.getData();
	}
}
