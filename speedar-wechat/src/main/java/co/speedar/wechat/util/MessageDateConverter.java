package co.speedar.wechat.util;

import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * @author lixuanbin
 * @creation 2012-11-21
 */
@Component("messageDateConverter")
public class MessageDateConverter implements Converter {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class clazz) {
		return java.util.Date.class == clazz;
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		java.util.Date date = (java.util.Date) value;
		writer.setValue(String.valueOf(date.getTime() / 1000));
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext arg1) {
		String dateSecondString = reader.getValue();
		long dateSeconds = Long.parseLong(dateSecondString);
		java.util.Date resultDate = new java.util.Date(dateSeconds * 1000);
		return resultDate;
	}

}