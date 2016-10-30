package xiaogu.cash.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class PropertiesUtil {
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	private static Properties properties = null;
	static {
		init();
	}
	
	
	private PropertiesUtil() {
	}

	private static void init() {
		properties = new Properties();
		InputStream in = 
				PropertiesUtil.class.getClassLoader().getResourceAsStream("wechat.properties");
		try {
			properties.load(in);
		} catch (IOException e) {
			logger.error("读取配置文件出错 {}",e);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}
	
	public static String getValue(String key) {
		String property = properties.getProperty(key);
		try {
			property = new String(property.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		return property;
	}
}


