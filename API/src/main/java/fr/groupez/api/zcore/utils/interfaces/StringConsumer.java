package fr.groupez.api.zcore.utils.interfaces;

@FunctionalInterface
public interface StringConsumer<T> {

	String accept(T t);
	
}
