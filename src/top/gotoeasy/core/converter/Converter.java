package top.gotoeasy.core.converter;

public interface Converter<FROM, TO> {

	public TO convert(FROM orig);
}
