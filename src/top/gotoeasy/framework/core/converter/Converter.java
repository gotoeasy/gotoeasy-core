package top.gotoeasy.framework.core.converter;

public interface Converter<FROM, TO> {

	public TO convert(FROM orig);
}
