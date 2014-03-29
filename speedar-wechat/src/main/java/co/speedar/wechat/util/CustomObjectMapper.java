package co.speedar.wechat.util;

import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.stereotype.Component;

@Component
public class CustomObjectMapper extends ObjectMapper {

	public CustomObjectMapper() {
		configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
	}
}