/**
 * 
 */
package org.demo.netty.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

/**
 * 
 * @author yue
 *
 */
public class JacksonJsonSupport implements JsonSupport{

	protected final ObjectMapper objectMapper = new ObjectMapper();
	
	public JacksonJsonSupport(Module... modules) {
		if (modules != null && modules.length > 0) {
			objectMapper.registerModules(modules);
		}
		init();
	}
	
	private void init() {
		objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);
	}

	@Override
	public <T> T read(ByteBufInputStream is, Class<T> clazz) throws IOException {
		return objectMapper.readValue((DataInput)is, clazz);
	}

	@Override
	public void write(ByteBufOutputStream out, Object value) throws IOException {
		objectMapper.writeValue((DataOutput)out, value);
	}

	
	@Override
	public Object read(Object o) throws IOException {
		if (null == o) {
			throw new IOException("解析对象不能为空.");
		}
		
		if (o instanceof String) {
			return objectMapper.readTree((String)o);
		} else if (o instanceof byte[]) {
			return objectMapper.readTree((byte[])o);
		}
		
		throw new IOException("仅支持解析JsonParser.");
	}
	
	@Override
	public <T> T readClass(Object o, Class<T> clazz) throws IOException {
		if (o == null) {
			throw new IOException("解析对象不能为空.");
		}
		
		if (o instanceof byte[]) {
			return objectMapper.readValue((byte[]) o, clazz);
		} else if (o instanceof JsonParser) {
			return objectMapper.readValue((JsonParser)o, clazz);
		} else if (o instanceof String) {
			return objectMapper.readValue((String)o, clazz);
		} else if (o instanceof JsonNode) {
			JsonNode jsonNode = (JsonNode)o;
			return objectMapper.readValue(jsonNode.traverse(), clazz);
		}
		throw new IOException("仅支持解析JsonParser.");
	}

	@Override
	public <T> T readClasses(Object o, Object ref) throws IOException {
		if (null == o) {
			throw new IOException("解析对象不能为空.");
		}
		
		if (o instanceof byte[]) {
			return (T) objectMapper.readValue((byte[]) o, (TypeReference<?>)ref);
		} else if (o instanceof String) {
			return (T) objectMapper.readValue((String) o, (TypeReference<?>)ref);
		} else if (o instanceof JsonParser) {
			return (T) objectMapper.readValue((JsonParser) o, (TypeReference<?>)ref);
		} else if (o instanceof JsonNode) {
			JsonNode jsonNode = (JsonNode) o;
			return (T) objectMapper.readValue(jsonNode.traverse(), (TypeReference<?>)ref);
		} 
		
		throw new IOException("仅支持解析类型String/JsonParser/JsonNode.");
	}

	@Override
	public String writeString(Object obj) throws IOException {
		
		return objectMapper.writeValueAsString(obj);
	}

	@Override
	public byte[] writeBytes(Object obj) throws IOException {
		
		return objectMapper.writeValueAsBytes(obj);
	}
}
