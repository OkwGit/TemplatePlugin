package fr.groupez.template.zcore.utils.interfaces;

@FunctionalInterface
public interface StringConsumer<T> {

	String accept(T t);
	
}
